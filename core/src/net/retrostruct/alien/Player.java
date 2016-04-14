package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by s on 4/14/16.
 */
public class Player extends Entity {

    private int jumpKey, shootKey; // Keyboard controls

    public void setJumpKey(int value) { jumpKey = value; }
    public void setShootKey(int value) { shootKey = value; }

    public Player() {
        super(0.0f, 0.0f);

        // Set controls
        setJumpKey(Input.Keys.SPACE);
        setShootKey(Input.Keys.ENTER);

        // Set texture region
        setTextureRegion(0, 0, 32, 32);
    }

    public void update(float delta, boolean mobile) {
        super.update(delta);

        // Check input
        if(mobile) {
            // TODO: Check touchscreen input
        } else {
            if(Gdx.input.isKeyPressed(jumpKey)) jump();
            if(Gdx.input.isKeyPressed(shootKey)) shoot();
        }
    }

    public void resize() {
        // Center player when window is resized
        setX((getWorldWidth() - getWidth()) / 2.0f);
    }

    @Override
    public void reset() {
        // Center player to screen
        setX((getWorldWidth() - getWidth()) / 2.0f);
        setY((getWorldHeight() - getHeight()) / 2.0f);
    }

    private void jump() {
        // TODO: Jump
    }

    private void shoot() {
        // TODO: Shoot
    }
}
