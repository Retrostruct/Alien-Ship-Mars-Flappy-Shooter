package net.retrostruct.alien;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

/**
 * Created by s on 4/14/16.
 */
public class EnemyShip extends Entity {

    private float speed = 150.0f; // Enemy ship speed
    private float amplitude, frequency;
    private float yOffset;

    public EnemyShip(Random random) {
        super(0.0f, 0.0f);

        // Set size
        setWidth(32);
        setHeight(32);

        setHitbox(32 / 4, 32 / 4, 32 / 2, 32 / 2);

        setOrigin(0, height/2);

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

        float newRotation = (MathUtils.cos(getX() * frequency) * amplitude) / MathUtils.PI / 2;

        setRotation(newRotation);

        if(getX() < 0 - getWidth()) kill();
    }
}