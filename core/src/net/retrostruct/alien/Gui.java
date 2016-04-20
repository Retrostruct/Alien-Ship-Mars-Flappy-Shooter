package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

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

        gameShoot.setLocalScale(2.0f);
        gameJump.setLocalScale(2.0f);

        gameJump.setHitbox(-(int)gameJump.getWidth(), 0, 64 ,64);
        gameShoot.setHitbox(0, 0, 64 ,64);

        //Set the correct texture region (multiply by the width and height of the button textures)
        gameShoot.setTextureRegion(1 * 32,1 * 32);
        gameJump.setTextureRegion(0, 1 * 32);

        //Position the jump button in the lower left
        float lowerLeft = 0 + gameJump.getWidth();
        gameJump.setX(lowerLeft);

        //Position the shoot button in the lower right of the corner
        float lowerRight = gameShoot.getWorldWidth() - gameShoot.getWidth()*2;
        gameShoot.setX(lowerRight);
    }

    public Game.GameStates getNextState() {
        // TODO: Check witch next state is
        return Game.GameStates.Menu;
    }

    public void menuUpdate(){

    }
    Rectangle input = new Rectangle(0, 0, 0, 0);


    float jumpCoolDown = 0.1f;
    float jumpTimer = 0;
    float shootCoolDown = 0.1f;
    float shootTimer = 0;

    //Updates the game gui (should only be run if the game state is equal to "playing"
    public void gameUpdate(Camera camera, float delta){

        //Set the buttons to be not pressed by standard
        shootPressed = false;
        jumpPressed = false;

        shootTimer += delta;
        jumpTimer += delta;

        if(Game.MOBILE) {
            for(int i = 0; i < 10; i++) {
                //Create a rectangle for the input from the player
                Vector3 mousePosition = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(mousePosition);
                input = new Rectangle(mousePosition.x, mousePosition.y, 1, 1);


                //Check if shoot button is pressed
                if (input.overlaps(gameShoot.getHitbox()) && Gdx.input.isTouched(i) && shootTimer > shootCoolDown) {
                    shootPressed = true;
                    shootTimer = 0;
                }

                //Check if jump button is pressed
                if (input.overlaps(gameJump.getHitbox()) && Gdx.input.isTouched(i) && jumpTimer > jumpCoolDown) {
                    jumpPressed = true;
                    jumpTimer = 0;
                }
            }

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

    public void drawButtonHitboxes(ShapeRenderer shapeRenderer) {
        gameShoot.drawHitBox(shapeRenderer);
        gameJump.drawHitBox(shapeRenderer);
    }

}
