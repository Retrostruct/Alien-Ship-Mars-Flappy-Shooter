package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by s on 4/14/16.
 */
public class Player extends Entity {

    private int jumpKey, shootKey; // Keyboard controls

    public void setJumpKey(int value) { jumpKey = value; }
    public void setShootKey(int value) { shootKey = value; }

    private float gravity = 25.0f;
    private float jumpHeight = 500.0f;

    private int score;

    // Score methods
    public void addScore() { score++; }
    public void addScore(int i) { score += i; }

    public Rectangle getRectangle() {
        return new Rectangle(getX() + getWidth() / 4,
                getY() + getHeight() / 4,
                getWidth() / 2, getHeight() / 2);
    }

    public Player() {
        super(0.0f, 0.0f);

        // Set controls
        setJumpKey(Input.Keys.SPACE);
        setShootKey(Input.Keys.ENTER);

        // Set size
        setWidth(32);
        setHeight(32);

        // Set texture region
        setTextureRegion(0, 0);
    }

    public void update(float delta, boolean mobile, Array<Entity> entities, Gui gui) {
        super.update(delta, this);

        // Add gravity to velocity
        addVelocity(0.0f, -gravity);

        // Check input
        /*
        This code is now obsolete. Input for jumping is handled by the gui (only works for phones at the time)

        if(mobile) {
            if(Gdx.input.justTouched()) jump();
        } else {
            if(Gdx.input.isKeyJustPressed(jumpKey)) jump();
            if(Gdx.input.isKeyPressed(shootKey)) shoot(entities);
        }
        */

        if(gui.jumpPressed) jump();
        if(gui.shootPressed) shoot(entities);

        // Set rotation
        setRotation(-getVelocity().y * 0.01f - (float) Math.toRadians(30.0));

        if(getY() > getWorldHeight() - getHeight()) setVelocityY(0.0f);
        setY(MathUtils.clamp(getY(), -getHeight(), getWorldHeight() - getHeight()));

        // TEST
        if(getY() < 0) jump();

        if(getY() < - getHeight()) kill();
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

        // Reset velocity
        setVelocity(0.0f, 0.0f);

        score = 0;

        // Revive player
        revive();
    }

    private void jump() {
        setVelocityY(jumpHeight);
    }

    private void shoot(Array<Entity> entities) {
        entities.add(new Bullet(getX(), getY(), (float) Math.toRadians(getRotation())));
    }
}
