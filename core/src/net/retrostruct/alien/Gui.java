package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by kasper.esbjornsson on 2016-04-18.
 */
public class Gui {

    //Button entities
    Entity gameShoot, gameJump, menuPlay;
    BitmapFont font;
    Vector2 fontPos;
    int playerScore;
    int gameOverScore;
    int highScore;


    //Bools for button input checks
    boolean shootPressed;
    boolean jumpPressed;

    //Set the score to be shown on game over screen
    public void setGameOverScore(int score){
        gameOverScore = score;

        if(gameOverScore > highScore)
            highScore = gameOverScore;
    }

    //Create a new Gui
    public Gui(){

        highScore = 0;
        //Load font
        font = new BitmapFont(Gdx.files.internal("dialogue/crunchyFont.fnt"));

        //Create the buttons at default position (0,0)
        gameShoot = new Entity(0,0);
        gameJump = new Entity(0,0);
        menuPlay = new Entity(0,0);

        //Set the with and height of both buttons
        gameShoot.setWidth(32);
        gameShoot.setHeight(32);
        menuPlay.setHeight(32);

        gameJump.setWidth(32);
        gameJump.setHeight(32);
        menuPlay.setWidth(32);

        gameShoot.setLocalScale(2.0f);
        gameJump.setLocalScale(2.0f);
        menuPlay.setLocalScale(2.0f);

        int w = (int) gameJump.getWidth();
        int h = (int) gameJump.getHeight();

        gameJump.setHitbox(-w / 2, -h / 2, 64 ,64);
        gameShoot.setHitbox(-w / 2, -h / 2, 64 ,64);

        //Set the correct texture region (multiply by the width and height of the button textures)
        gameShoot.setTextureRegion(1 * 32,1 * 32);
        gameJump.setTextureRegion(0, 1 * 32);
        menuPlay.setTextureRegion(2 * 32, 1 * 32);

        //Position the jump button in the lower left
        float lowerLeft = w / 2;
        gameJump.setX(lowerLeft);

        //Position the shoot button in the lower right of the corner
        float lowerRight = gameShoot.getWorldWidth() - w * 1.5f;
        gameShoot.setX(lowerRight);

        float buttonY = h / 2;

        gameJump.setY(buttonY);
        gameShoot.setY(buttonY);

        //Position the play button in the middle of the screen
        //Todo: Fix the hitbox for play button (not using the local scale?)
        menuPlay.setX((Entity.getWorldWidth() - menuPlay.getWidth()) / 2);
        menuPlay.setY((Entity.getWorldHeight() - menuPlay.getHeight()) / 2);
    }

    public Game.GameStates getNextState(Game.GameStates current, OrthographicCamera camera) {

        if(current == Game.GameStates.Menu){

            if(!Game.MOBILE) if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) current = Game.GameStates.Infinite;
            //Run menu update

            float rawX = Gdx.input.getX();
            float rawY = Gdx.input.getY();

            Vector3 rawCoords = new Vector3(rawX, rawY, 0);

            Vector3 unprojectedCoords = camera.unproject(rawCoords);

            if(menuPlay.getHitbox().contains(unprojectedCoords.x, unprojectedCoords.y) && Gdx.input.justTouched())
                current = Game.GameStates.Infinite;

            font.getData().setScale(0.5f*Entity.gameScale, 0.5f*Entity.gameScale);
        }

        return current;
    }

    public void menuUpdate(){

    }

    public void gameOverUpdate(){

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
    public void gameUpdate(Camera camera, float delta, Player player){

        //Set the buttons to be not pressed by standard
        jumpPressed = false;
        shootPressed = false;

        shootTimer += delta;

        playerScore = player.getScore();

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
            if(Gdx.input.isKeyPressed(Player.getShootKey()) && shootTimer > 0.5f) {
                shootTimer = 0;
                shootPressed = true;
            }
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
        fontPos = new Vector2(20, Entity.getWorldHeight() - glyphLayout.height - 20);
        font.draw(spriteBatch, "Score: " + playerScore, fontPos.x, fontPos.y); //Draw the player's score

        //Only draw buttons for jumping and shooting if playing on a mobile device
        if(!Game.MOBILE) return;
        gameShoot.draw(spriteBatch);
        gameJump.draw(spriteBatch);
    }

    GlyphLayout glyphLayout = new GlyphLayout();
    public void menuDraw(SpriteBatch spriteBatch, OrthographicCamera camera){

        String writtenHS;
        writtenHS = "Highest score this session: " + highScore;

        glyphLayout.setText(font, writtenHS);

        float x = (Entity.getWorldWidth() - glyphLayout.width) / 2;
        float y = (Entity.getWorldHeight() - glyphLayout.height) / 2 - menuPlay.height - glyphLayout.height;

        font.draw(spriteBatch, writtenHS, x, y);

        menuPlay.draw(spriteBatch);
    }

    public void drawButtonHitboxes(ShapeRenderer shapeRenderer) {
        //if(!Game.MOBILE) return;
        gameShoot.drawHitBox(shapeRenderer);
        gameJump.drawHitBox(shapeRenderer);
        menuPlay.drawHitBox(shapeRenderer);
    }

}
