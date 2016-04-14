package net.retrostruct.alien;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class Game extends ApplicationAdapter {

    private final boolean mobile = false; // TODO: Set to true on release
    private final String TITLE = "Alien Shooter"; // Title of window
    private final float SCALE = 2.0f; // Change the game's scale here

    private SpriteBatch spriteBatch; // Sprite batch to draw entities
    private OrthographicCamera camera; // Camera to translate coordinates
    private Viewport viewport; // Game's viewport
    private Random random;

    // The game's states
    private enum GameStates {
        Menu,
        Playing,
        Credits
    }

    private GameStates currentGameState = GameStates.Menu;

    private Color clearColor = Color.WHITE; // Screen clear color

    private Player player; // Player
    private ScrollingBackground background; // Scrolling background

    // Entity array to hold all objects except the player
    private Array<Entity> entities = new Array<Entity>();
	
	@Override
	public void create () {
        int width = Gdx.graphics.getWidth(); // Get window width
        int height = Gdx.graphics.getHeight(); // Get window height

        Gdx.graphics.setTitle(TITLE); // Set window title

		spriteBatch = new SpriteBatch(); // Create sprite batch
        camera = new OrthographicCamera(); // Create orthographic camera
        viewport = new FillViewport(width, height, camera); // Create the fill viewport
        random = new Random(System.currentTimeMillis()); // Create and seed the randomizer

		Entity.loadSpriteSheet("sheets/entities.png"); // Set entity sprite sheet
		Entity.setScale(SCALE); // Set entity scale
		Entity.setViewport(viewport); // Update entity viewport

        player = new Player(); // Create player

        // Create scrolling background
        // background = new ScrollingBackground("sheets/backgrounds.png", SCALE);

        reset(); // Reset the game
	}

    private void reset() {
        player.reset(); // Reset player
        entities.clear(); // Clear entity array

        entities.add(new EnemyShip(random));
    }

    private void update(float delta) {
        // Switch the current game state
        switch(currentGameState) {
            case Menu:

                break;
            case Playing:

                background.update(delta); // Update scrolling background
                player.update(delta); // Update player

                // Update entities
                for(Entity entity: entities) {
                    entity.update(delta);

                    // Remove entity if killed
                    if(!entity.isAlive()) {
                        entities.removeValue(entity, true);
                        Gdx.app.log("ENTITY", "Entity removed!");
                        continue;
                    }
                }

                break;
            case Credits:

                break;
        }
    }

    private void draw() {
        // Switch the current game state
        switch(currentGameState) {
            case Menu:

                break;
            case Playing:

                background.draw(spriteBatch); // Draw scrolling background
                player.draw(spriteBatch); // Draw player

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
        update(Gdx.graphics.getDeltaTime()); // Update

        // Clear
		Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw
		spriteBatch.begin();
        draw();
		spriteBatch.end();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Update viewport width new size
        Entity.setViewport(viewport); // Set viewport for entities
        player.resize(); // Re-calculate player position
    }

}
