package net.retrostruct.alien;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class Game extends ApplicationAdapter {

    public static boolean MOBILE;
    public static boolean RELEASE = false; // TODO: Set to true on release

    private final String TITLE = "Alien Ship Mars Flappy Shooter"; // Title of window
    private float SCALE; // Change the game's scale here

    private SpriteBatch spriteBatch; // Sprite batch to draw entities
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera; // Camera to translate coordinates
    private Viewport viewport; // Game's viewport
    private Random random;
    private EntityCounter entityCounter; // Counts entities

    private AudioHandler audioHandler; // Handles all audio effect and music

    // The game's states
    public static enum GameStates {
        Menu, // Restart screen (press space/tap to play)
        Infinite, // Infinite mode
        Story, // Story mode
        Hamster, // Hamster mode
        Credits, // Roll credits
    }

    private GameStates currentGameState = GameStates.Infinite; // Set current state to menu

    private Color clearColor = Color.BLACK; // Screen clear color

    private Player player; // Player
    private Gui gui; //Game gui
    private ScrollingBackground background; // Scrolling background

    // Entity array to hold all objects except the player
    private Array<Entity> entities = new Array();

    //Bullet array to hold all bullets (allows for iterating through entities to check collision)
    private Array<Bullet> bullets = new Array();

    Timer enemyTimer = new Timer(10.0f);

    float time = 0.0f;
	
	@Override
	public void create () {
        int width = Gdx.graphics.getWidth(); // Get window width
        int height = Gdx.graphics.getHeight(); // Get window height

        SCALE = height / 32.0f / 8.0f;

        LOG("GAME", "Scale: " + SCALE);
        // 1.875 on desktop
        // 4.21875 on mobile

        Gdx.graphics.setTitle(TITLE); // Set window title

		spriteBatch = new SpriteBatch(); // Create sprite batchprivate
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(); // Create orthographic camera
        camera.setToOrtho(false, width, height);
        camera.update();
        viewport = new FillViewport(width, height, camera); // Create the fill viewport
        random = new Random(System.nanoTime()); // Create and seed the randomizer
        entityCounter = new EntityCounter(); // Counts entity types

		Entity.loadSpriteSheet("sheets/entities.png"); // Set entity sprite sheet
		Entity.setGameScale(SCALE); // Set entity scale
		Entity.setViewport(viewport); // Update entity viewport

        player = new Player(); // Create player
        gui = new Gui();

        audioHandler = new AudioHandler();
        audioHandler.playMusic();
        // Create scrolling background
        background = new ScrollingBackground("sheets/backgrounds.png", SCALE);

        reset(); // Reset the game
	}

    private void reset() {
        player.reset(); // Reset player
        entities.clear(); // Clear entity array
        bullets.clear(); // Clear bullet array

        entityCounter.addEnemies(time, random, entities);

        time = 0.0f;

        for(int i = 0; i < 10; i++) entityCounter.addEntity(new Hamster(random), entities);
    }

    private void update(float delta) {
        // Switch the current game state
        switch(currentGameState) {
            case Menu:

                // Set game state to next game state
                currentGameState = gui.getNextState();

                break;
            case Infinite:

                time += delta;
                if(enemyTimer.tick(delta)) entityCounter.addEnemies(time, random, entities);

                background.update(delta); // Update scrolling background
                player.update(delta, MOBILE, entities); // Update player

                gui.gameUpdate(camera, delta);

                if(gui.jumpPressed) {
                    player.jump();
                    audioHandler.playSound("jump");
                }
                
                if(gui.shootPressed) {
                    player.shoot(bullets);
                    audioHandler.playSound("shoot");
                }

                // Update entities
                for(Entity entity: entities) {
                    entity.update(delta, player);

                    //Check if entity is enemy ship or hamster (no unnecesary bullet iterating)
                    if(entity instanceof EnemyShip ||
                            entity instanceof Hamster) {
                        //Check collision for all bullets against all enemy ships and hamsters
                        for(Bullet bullet: bullets){
                            if(bullet.overlaps(entity)) {
                                entity.kill();
                                audioHandler.playSound("explosion");
                            }
                        }
                    }

                    // Remove entity if killed
                    if(!entity.isAlive()) {
                        entityCounter.removeEntity(entity, entities);
                        continue;
                    }
                }
                //Update bullets
                for(Bullet bullet: bullets){
                    bullet.update(delta, player);
                    if(!bullet.isAlive()) {
                        bullets.removeValue(bullet, true);
                        continue;
                    }
                }

                if(!player.isAlive()) {
                    reset();
                    LOG("GAME", "Player died!");
                }

                break;
            case Credits:
                // TODO: Roll credits
                break;
        }
    }

    private void draw() {
        // Switch the current game state
        switch(currentGameState) {
            case Menu:

                break;
            case Infinite:

                background.draw(spriteBatch, player); // Draw scrolling background
                player.draw(spriteBatch); // Draw player

                // Draw entities
                for(Entity entity: entities) {
                    entity.draw(spriteBatch);
                }
                for (Bullet bullet: bullets){
                    bullet.draw(spriteBatch);
                }

                gui.gameDraw(spriteBatch);

                break;
            case Credits:

                break;
        }
    }

	@Override
	public void render () {
        // Allow the game to exit
        if(!RELEASE) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
                Gdx.app.exit();
        }

        camera.update();
        update(Gdx.graphics.getDeltaTime() * SCALE * 0.75f); // Update

        // Clear
		Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw
		spriteBatch.begin();
        draw();
		spriteBatch.end();

        if(!RELEASE) renderShapes();
	}

    private void renderShapes() {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for(Entity entity: entities) {
            entity.drawHitBox(shapeRenderer);
        }

        for(Bullet bullet: bullets) {
            bullet.drawHitBox(shapeRenderer);
        }

        player.drawHitBox(shapeRenderer);

        gui.drawButtonHitboxes(shapeRenderer);

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Update viewport wiTechnical detailsdth new size
        Entity.setViewport(viewport); // Set viewport for entities
        player.resize(); // Re-calculate player position
    }

    public static void LOG(String tag, String message) {
        if(RELEASE) return;
        Gdx.app.log(tag, message);
    }

}
