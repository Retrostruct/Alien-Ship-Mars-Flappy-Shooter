package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by kasper.esbjornsson on 2016-04-18.
 */
public class Gui {

    //Button entities
    Entity gameShoot, gameJump;

    //Bools for button input checks
    boolean shootPressed;
    boolean jumpPressed;

    //Create a new Gui
    public Gui(){

        //Create the buttons at default position (0,0)
        gameShoot = new Entity(0,0);
        gameJump = new Entity(0,0);

        //Set the with and height of both buttons
        gameShoot.setWidth(32);
        gameShoot.setHeight(32);

        gameJump.setWidth(32);
        gameJump.setHeight(32);

        //Set the correct texture region (multiply by the width and height of the button textures)
        gameShoot.setTextureRegion(1 * 32,1 * 32);
        gameJump.setTextureRegion(0, 1 * 32);

        //Position the shoot button in the lower right of the corner
        int lowerRight = (int)gameShoot.getWorldWidth() - (int)gameShoot.getWidth();
        gameShoot.setX(lowerRight);
    }

    public void menuUpdate(){

    }

    //Updates the game gui (should only be run if the game state is equal to "playing"
    public void gameUpdate(){

        //Set the buttons to be not pressed by standard
        shootPressed = false;
        jumpPressed = false;

        if(Game.MOBILE) {
            //Create a rectangle for the input from the player
            Rectangle inputCollision = new Rectangle(Gdx.input.getX(),Gdx.input.getY() - Entity.getWorldHeight() + gameShoot.getHeight(),10,10);

            //Check if shoot button is pressed
            if(inputCollision.overlaps(gameShoot.getRectangle()) && Gdx.input.justTouched()){
                shootPressed = true;
            }

            //Check if jump button is pressed
            if(inputCollision.overlaps(gameJump.getRectangle()) && Gdx.input.justTouched()){
                jumpPressed = true;
            }

            //Move the input collision outside the active game area
            inputCollision.x = -10;
            inputCollision.y = -10;
        } else {
            if(Gdx.input.isKeyJustPressed(Player.getJumpKey()))
                jumpPressed = true;
            if(Gdx.input.isKeyJustPressed(Player.getShootKey()))
                shootPressed = true;
        }



    }

    public boolean jumpButtonPressed(){
        return  jumpPressed;
    }

    public boolean shootButtonPressed(){
        return  shootPressed;
    }

    //Draws the game gui. Should only run if game state is equal to "playing"
    public void gameDraw(SpriteBatch spriteBatch){
        gameShoot.draw(spriteBatch);
        gameJump.draw(spriteBatch);
    }

}
