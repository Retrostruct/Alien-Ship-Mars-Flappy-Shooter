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

    private final String TITLE = "Alien Ship Mars Flappy Shooter"; // Title of window
    private float SCALE; // Change the game's scale here

    private SpriteBatch spriteBatch; // Sprite batch to draw entities
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera; // Camera to translate coordinates
    private Viewport viewport; // Game's viewport
    private Random random;
    private EntityCounter entityCounter;

    // The game's states
    private enum GameStates {
        Menu, // Restart screen (press space/tap to play)
        Playing, // Play the game
        Credits, // Roll credits
    }

    private GameStates currentGameState = GameStates.Playing; // Set current state to menu

    private Color clearColor = Color.BLACK; // Screen clear color

    private Player player; // Player
    private ScrollingBackground background; // Scrolling background
    private Gui gui;

    // Entity array to hold all objects except the player
    private Array<Entity> entities = new Array();

    Timer enemyTimer = new Timer(1.0f);

    float t = 0.0f;
    int getNewEnemyCount(float t) {
        return (int) (t / 100.0f) + 1;
    }

    void addEnemies(int n) {
        for(int i = 0; i < n; i++) {
            entityCounter.addEntity(new EnemyShip(random), entities);
        }
    }
	
	@Override
	public void create () {
        int width = Gdx.graphics.getWidth(); // Get window width
        int height = Gdx.graphics.getHeight(); // Get window height

        SCALE = height / 32.0f / 8.0f;

        Gdx.graphics.setTitle(TITLE); // Set window title

		spriteBatch = new SpriteBatch(); // Create sprite batch
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera(); // Create orthographic camera
        viewport = new FillViewport(width, height, camera); // Create the fill viewport
        random = new Random(System.nanoTime()); // Create and seed the randomizer
        entityCounter = new EntityCounter(); // Counts entity types

		Entity.loadSpriteSheet("sheets/entities.png"); // Set entity sprite sheet
		Entity.setScale(SCALE); // Set entity scale
		Entity.setViewport(viewport); // Update entity viewport

        player = new Player(); // Create player

        gui = new Gui();

        // Create scrolling background
        // background = new ScrollingBackground("sheets/backgrounds.png", SCALE);

        reset(); // Reset the game
	}

    private void reset() {
        player.reset(); // Reset player
        entities.clear(); // Clear entity array

        addEnemies(1);

        t = 0.0f;

        for(int i = 0; i < 10; i++) entityCounter.addEntity(new Hamster(random), entities);
    }

    private void update(float delta) {
        // Switch the current game state
        switch(currentGameState) {
            case Menu:

                break;
            case Playing:

                t += delta;
                if(enemyTimer.tick(delta)) addEnemies(getNewEnemyCount(t));

                gui.gameUpdate();

                // background.update(delta); // Update scrolling background
                player.update(delta, MOBILE, entities, gui); // Update player

                // Update entities
                for(Entity entity: entities) {
                    entity.update(delta, player);

                    // Remove entity if killed
                    if(!entity.isAlive()) {
                        entityCounter.removeEntity(entity, entities);
                        continue;
                    }
                }

                if(!player.isAlive()) {
                    reset();
                    Gdx.app.log("GAME", "Player died!");
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
            case Playing:

                // background.draw(spriteBatch); // Draw scrolling background
                player.draw(spriteBatch); // Draw player
                gui.gameDraw(spriteBatch); //Draw the GUI

                // Draw entities
                for(Entity entity: entities) {
                    entity.draw(spriteBatch);
                }

                break;
            case Credits:

                break;
        }
    }

	@Override
	public void render () {
        // Allow the game to exit
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();


        update(Gdx.graphics.getDeltaTime()); // Update

        // Clear
		Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw
		spriteBatch.begin();
        draw();
		spriteBatch.end();

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for(Entity entity: entities) {
            entity.drawHitBox(shapeRenderer);
        }

        player.drawHitBox(shapeRenderer);

        shapeRenderer.end();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Update viewport width new size
        Entity.setViewport(viewport); // Set viewport for entities
        player.resize(); // Re-calculate player position
    }

}
