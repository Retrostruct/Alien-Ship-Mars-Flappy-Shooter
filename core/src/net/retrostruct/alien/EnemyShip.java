package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

/**
 * Created by s on 4/14/16.
 */
public class EnemyShip extends Entity {

    private float speed = 10.0f; // Enemy ship speed

    public EnemyShip(Random random) {
        super(0.0f, 0.0f);

        // Randomize position
        setX(getWorldWidth() + getWorldWidth() * random.nextFloat());
        setY(getWorldHeight() * random.nextFloat());

        setTextureRegion(32 * 1, 0, 32, 32); // Set texture region
        setVelocity(-speed, 0); // Set initial velocity
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setY(MathUtils.sin(getX()));

        Gdx.app.log("ENEMY", "X: " + getX() + ", Y: " + getY());
    }

    @Override
    public String toString() {
        return "Enemy Ship";
    }
}
