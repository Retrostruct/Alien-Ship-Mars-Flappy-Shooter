package net.retrostruct.alien;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by s on 4/14/16.
 */
public class Player extends Entity {

    private static int jumpKey, shootKey; // Keyboard controls

    public static void setJumpKey(int value) { jumpKey = value; }
    public static void setShootKey(int value) { shootKey = value; }

    public static int getJumpKey() { return jumpKey; }
    public static int getShootKey() { return shootKey; }

    private float gravity = 18.0f;
    private float jumpHeight = 450.0f;

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

    public void update(float delta, boolean mobile, Array<Entity> entities) {
        super.update(delta, this);

        // Add gravity to velocity
        addVelocity(0.0f, -gravity);

        // Set rotation
        setRotation(-getVelocity().y * 0.021f - (float) Math.toRadians(35.0));

        if(getY() > getWorldHeight() - getHeight()) setVelocityY(0.0f);
        setY(MathUtils.clamp(getY(), -getHeight(), getWorldHeight() - getHeight()));

        if(isOnScreen() == false) kill();
    }

    public void resize() {
        // Center player when window is resized
        //setX((getWorldWidth() - getWidth()) / 2.0f);
        setX(getWidth()*4);
    }

    @Override
    public void reset() {
        // Center player to screen
        //setX((getWorldWidth() - getWidth()*2) / 2.0f);
        setY((getWorldHeight() - getHeight()) / 2.0f);
        setX(getWidth()*4);

        // Reset velocity
        setVelocity(0.0f, 0.0f);

        score = 0;

        // Revive player
        revive();
    }

    public void jump() {
        setVelocityY(jumpHeight);
    }

    public void shoot(Array<Bullet> bullets) {
        bullets.add(new Bullet(getX(), getY(), (float) Math.toRadians(getRotation())));
    }
}
