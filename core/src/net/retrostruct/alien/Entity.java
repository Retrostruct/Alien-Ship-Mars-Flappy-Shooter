package net.retrostruct.alien;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by s on 4/14/16.
 */
public class Entity {

    private static Texture spriteSheet; // Entity sprite sheet
    private static Viewport viewport; // Game's viewport
    private static float scale; // Game's scale

    private Vector2 position, velocity, origin; // Position, velocity and origin
    private int width, height; // Size of entity
    private float rotation = 0.0f; // Rotation (initially 0)
    private TextureRegion textureRegion; // Texture region of entity

    private boolean alive = true; // Is the entity alive

    // Load sprite sheet
    public static void loadSpriteSheet(String path) { spriteSheet = new Texture(path); }

    // Viewport
    public static float getWorldWidth() { return viewport.getWorldWidth(); }
    public static float getWorldHeight() { return viewport.getWorldHeight(); }

    public static void setViewport(Viewport value) { viewport = value; }

    // Scale
    public float getScale() { return scale; }
    public static void setScale(float value) { scale = value; }

    // Position
    public float getX() { return position.x; }
    public void setX(float value) { position.x = value; }

    public float getY() { return position.y; }
    public void setY(float value) { position.y = value; }

    // Velocity
    public Vector2 getVelocity() { return velocity; }
    public void setVelocity(float x, float y) { velocity = new Vector2(x, y); }

    // Origin
    public Vector2 getOrigin() { return new Vector2(getWidth() / 2, getHeight() / 2); }

    // Rotation
    public float getRotation() { return rotation; }
    public void setRotation(float value) { rotation = value; }

    // Size
    public float getWidth() { return width * scale; }
    public void setWidth(int value) { width = value; }

    public float getHeight() { return height * scale; }
    public void setHeight(int value) { height = value; }

    // Texture region
    public TextureRegion getTextureRegion() { return textureRegion; }
    public void setTextureRegion(int x, int y, int width, int height) {
        textureRegion = new TextureRegion(spriteSheet, x, y, width, height);
    }

    // Alive
    public boolean isAlive() { return alive; }
    public void kill() { alive = false; }

    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public Entity(float x, float y) {
        position = new Vector2();
        velocity = new Vector2();
        origin = new Vector2();
        setX(x);
        setY(y);
    }

    public void update(float delta) {
        // Move entity
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    public void draw(SpriteBatch spriteBatch) {
        // Draw entity
        spriteBatch.draw(getTextureRegion(), getX(), getY(),
                        getOrigin().x, getOrigin().y,
                        getWidth(), getHeight(),
                        getScale(), getScale(), rotation);
    }

    // Reset method that should be overrided
    public void reset() { }

    @Override
    public String toString() {
        return "Entity";
    }
}