package net.retrostruct.alien;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by s on 4/20/16.
 */
public class Explosion extends Entity {

    private static final int frameCount = 4;
    private int currentFrame = 0;
    private float timer = 0.0f;
    private float interval = 10.0f;

    private static TextureRegion[] frames;

    public Explosion(float x, float y) {
        super(x, y);
    }

    public static void createFrames() {
        frames = new TextureRegion[frameCount];
        for(int i = 0; i < frameCount; i++) {
            int w = 32;
            int h = 32;
            frames[i] = new TextureRegion(getSpriteSheet(), w * i, h, w, h);
        }
    }

    public void update(float delta, Player player) {
        super.update(delta, player);

        // Update animation timer
        timer += delta;
        if(timer > interval) {
            // When animation has played, kill this entity
            if (currentFrame >= frameCount) kill();
            else {
                currentFrame++;
                timer -= interval;
            }
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(frames[currentFrame], getX(), getY(),
                getOrigin().x, getOrigin().y,
                width, height,
                gameScale * localScale, gameScale * localScale, rotation);
    }
}
