package net.retrostruct.alien;

/**
 * Created by s on 4/14/16.
 */
public class Bullet extends Entity {

    private float speed = 5.0f;

    public Bullet() {
        super(0.0f, 0.0f);

        setTextureRegion(4 * 32, 0, 32, 32);
        setVelocity(speed, 0);
    }

    @Override
    public void update(float delta) {

    }
}
