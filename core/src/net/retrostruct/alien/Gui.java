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

        int w = (int) gameJump.getWidth();
        int h = (int) gameJump.getHeight();

        gameJump.setHitbox(-w / 2, -h / 2, 64 ,64);
        gameShoot.setHitbox(-w / 2, -h / 2, 64 ,64);

        //Set the correct texture region (multiply by the width and height of the button textures)
        gameShoot.setTextureRegion(1 * 32,1 * 32);
        gameJump.setTextureRegion(0, 1 * 32);

        //Position the jump button in the lower left
        float lowerLeft = w / 2;
        gameJump.setX(lowerLeft);

        //Position the shoot button in the lower right of the corner
        float lowerRight = gameShoot.getWorldWidth() - w * 1.5f;
        gameShoot.setX(lowerRight);

        float buttonY = h / 2;

        gameJump.setY(buttonY);
        gameShoot.setY(buttonY);
    }

    public Game.GameStates getNextState() {
        // TODO: Check witch next state is
        return Game.GameStates.Menu;
    }

    public void menuUpdate(){

    }
    boolean wasTouched[] = new boolean[10];

    Rectangle getInputRectangle(int i, Camera camera) {
        Rectangle r = new Rectangle();
        Vector3 pos = new Vector3();
        pos.x = Gdx.input.getX(i);
        pos.y = Gdx.input.getY(i);
        camera.unproject(pos);
        r.x = pos.x;
        r.y = pos.y;

        return r;
    }

    float shootTimer = 0.0f;

    //Updates the game gui (should only be run if the game state is equal to "playing"
    public void gameUpdate(Camera camera, float delta){

        //Set the buttons to be not pressed by standard
        jumpPressed = false;
        shootPressed = false;

        shootTimer += delta;

        if(Game.MOBILE) {
            for(int i = 0; i < 10; i++) {

                if(Gdx.input.isTouched(i) && !wasTouched[i]) {

                    if(getInputRectangle(i, camera).overlaps(gameJump.getHitbox())) {
                        jumpPressed = true;
                        wasTouched[i] = true;
                    }
                } else if(!Gdx.input.isTouched(i)){
                    wasTouched[i] = false;
                }

                if(Gdx.input.isTouched(i) && getInputRectangle(i, camera).overlaps(gameShoot.getHitbox()) && shootTimer > 0.5f) {
                    shootTimer = 0;
                    shootPressed = true;
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
