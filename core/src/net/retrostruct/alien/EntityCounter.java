package net.retrostruct.alien;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by s on 4/15/16.
 */
public class EntityCounter {

    private int enemyShipCount = 0;
    private int bulletCount = 0;
    private int hamsterCount = 0;

    public EntityCounter() { }

    public void addEnemies(float time, Random random, Array<Entity> entities) {
        for(int i = 0; i < getNewEnemyCount(time); i++) {
            addEntity(new EnemyShip(random), entities);
        }
    }

    int getNewEnemyCount(float t) {
        return (int) (t/10)+1;
    }

    public void addEntity(Entity e, Array<Entity> entities) {
        entities.add(e);
        shift(e, 1);
        Game.LOG("ENTITY", e.toString() + " added!");
    }

    public void removeEntity(Entity e, Array<Entity> entities) {
        entities.removeValue(e, true);
        shift(e, -1);
        Game.LOG("ENTITY", e.toString() + " removed!");
    }

    private void shift(Entity e, int n) {
        if(e instanceof EnemyShip) enemyShipCount += n;
        else if(e instanceof Bullet) bulletCount += n;
        else if(e instanceof Hamster) hamsterCount += n;
    }
}
