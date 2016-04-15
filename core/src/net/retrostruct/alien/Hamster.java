package net.retrostruct.alien;

import java.util.Random;

/**
 * Created by s on 4/15/16.
 */
public class Hamster extends Entity {

    private float speed = 100.0f;

    public Hamster(Random random) {
        super(0.0f, 0.0f);

        // Set size
        setWidth(32);
        setHeight(32);

        setX(random.nextFloat() * 500 + getWorldWidth());
        setY(random.nextFloat() * (getWorldHeight() - getHeight()));

        // Set texture region
        setTextureRegion(3 * 32, 0);

        setVelocityX(-speed);
    }

    @Override
    public void update(float delta, Player player) {
        super.update(delta, player);

        if(overlaps(player)) {
            player.addScore();
            kill();
        }
    }
}
