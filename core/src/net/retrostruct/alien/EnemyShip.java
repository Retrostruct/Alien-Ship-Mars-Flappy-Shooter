package net.retrostruct.alien;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by s on 4/14/16.
 */
public class EnemyShip extends Entity {

    private float speed = 150.0f; // Enemy ship speed
    private float amplitude, frequency;
    private float yOffset;

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX() + getWidth() / 4,
                    getY() + getHeight() / 4,
                    getWidth() / 2, getHeight() / 2);
    }

    public EnemyShip(Random random) {
        super(0.0f, 0.0f);

        // Set size
        setWidth(32);
        setHeight(32);

        // Randomize position
        setX(getWorldWidth() + getWorldWidth() * random.nextFloat());
        yOffset = (getWorldHeight() - getHeight()) * random.nextFloat();

        amplitude = random.nextFloat() * 50 + 50;
        frequency = random.nextFloat() * 0.01f;

        // Set texture region
        setTextureRegion(32 * 1, 0); // Set texture region

        // Initially, set velocity to negative speed
        setVelocity(-speed, 0);
    }

    @Override
    public void update(float delta, Player player) {
        super.update(delta, player);

        // If enemy overlaps player, kill the player
        if(overlaps(player)) player.kill();

        // Set the y position as a sinus function of the x position
        setY(MathUtils.sin(getX() * frequency) * amplitude + yOffset);

        float newRotation = (MathUtils.sin(getX() * frequency) * amplitude) / MathUtils.PI / 2;

        setRotation(newRotation);

        if(getX() < 0 - getWidth()) kill();
    }
}
