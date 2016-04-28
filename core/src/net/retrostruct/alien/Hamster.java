package net.retrostruct.alien;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

/**
 * Created by s on 4/15/16.
 */
public class Hamster extends Entity {

    private float speed = 120.0f;
    private float wiggleIntensity = 0.05f;
    private float wiggleIntensityModifier;
    private float wiggleAmplitude = 50;

    public Hamster(Random random) {
        super(0.0f, 0.0f);

        // Set size
        setWidth(32);
        setHeight(32);

        setX(random.nextFloat() * 500 + getWorldWidth());
        setY(random.nextFloat() * (getWorldHeight() - getHeight()));

        // Set texture region
        setTextureRegion(3 * 32, 0);

        wiggleIntensityModifier = random.nextFloat() * 0.04f;

        float speedModifier = random.nextFloat() * 30;

        setOrigin(width/2, height/2);

        setVelocityX(-(speed+speedModifier));
    }

    @Override
    public void update(float delta, Player player) {
        super.update(delta, player);

        if(overlaps(player)) {
            player.addScore();
            kill();
        }

        wiggle();

        if(getX() < 0 - getWidth()) kill();
    }

    private void wiggle(){
        setRotation((MathUtils.cos(getX() * (wiggleIntensity+wiggleIntensityModifier)) * wiggleAmplitude) / gameScale);
    }
}
