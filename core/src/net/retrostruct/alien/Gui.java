package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kasper.esbjornsson on 2016-04-18.
 */
public class Gui {

    Entity gameShoot, gameJump;

    public Gui(){

        gameShoot = new Entity(0,0);
        gameJump = new Entity(0,0);

        gameShoot.setTextureRegion(1,1);
        gameJump.setTextureRegion(0,1);
    }

    public void menuUpdate(){

    }

    //Updates the game gui
    public void gameUpdate(Player player){

    }

    public void gameDraw(SpriteBatch spriteBatch){
        gameShoot.draw(spriteBatch);
        gameJump.draw(spriteBatch);
    }

}
