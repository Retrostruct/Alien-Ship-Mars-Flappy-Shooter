package net.retrostruct.alien;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by s on 4/14/16.
 */
public class ScrollingBackground {

    private Texture spriteSheet; // Background sprite sheet
    private TextureRegion front, back; // Both backgrounds
    private int width, height; // Size of the individual backgrounds
    private float scale; // The game's scale
    private float speed = 50.0f; // Movement speed
    private float offset = 0.0f; // Offset (0.0f initially)

    public ScrollingBackground(String path, float scale) {
        spriteSheet = new Texture(path); // Load sheet
        this.scale = scale; // Set scale

        // Get sprite sheet size
        width = spriteSheet.getWidth();
        height = spriteSheet.getHeight() / 2;

        // Create regions
        back = new TextureRegion(spriteSheet, 0, 0, width, height);
        front = new TextureRegion(spriteSheet, 0, height, width, height);
    }

    public void update(float delta) {
        offset -= speed * delta; // Move the background
    }

    public void draw(SpriteBatch spriteBatch, Player player) {

        float y = (player.getWorldHeight() / 2 - player.getY()) * 0.05f;

        // Draw scrolling backgrounds
        for(int i = 0; i < 2; i++) {
            spriteBatch.draw(back, player.getWorldWidth() * i + (offset * 0.7f) % player.getWorldWidth(), y * 0.7f, 0, 0, width, height, scale, scale, 0.0f);
            spriteBatch.draw(front, player.getWorldWidth() * i + offset % player.getWorldWidth(), y, 0, 0, width, height, scale, scale, 0.0f);
        }
    }
}
