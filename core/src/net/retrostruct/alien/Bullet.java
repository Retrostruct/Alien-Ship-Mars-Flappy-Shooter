package net.retrostruct.alien;

/**
 * Created by s on 4/14/16.
 */
public class Bullet extends Entity {

    private float speed = 580.0f;

    public Bullet(float x, float y, float rotation) {
        super(x, y);

        // Set size
        setWidth(12);
        setHeight(4);

        // Bigger hitbox to make shooting enemies easier
        setHitbox(0, -10, 12, 14);

        // Set texture region
        setTextureRegion(138, 14);

        // Initially, set velocity to speed
        setVelocity((float) Math.cos(rotation) * speed, (float) Math.sin(rotation) * speed);

        // Set rotation
        setRotation(rotation);
    }

    @Override
    public void update(float delta, Player player) {
        super.update(delta, player);

        // Remove if outside screen
        if(getX() > getWorldWidth() + getWidth()) kill();
    }

    @Override
    public String toString() {
        return Bullet.class.getSimpleName();
    }
}
