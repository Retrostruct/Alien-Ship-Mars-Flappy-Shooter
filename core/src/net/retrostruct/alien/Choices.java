package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by sebastian.fransson on 2015-11-19.
 */
public class Choices {
    Rectangle rectangle;
    Texture image;
    Texture image2;
    String answer;
    boolean activated = false;
    Boolean pastTouch = false;
    public int number;
    boolean soundActivated = false;

    Sound sound;
    Sound sound2;


    public Choices(Vector2 position, Vector2 size, String answer, int number){
        image = new Texture(Gdx.files.internal("dialogue/blackBar.png"));
        image2 = new Texture(Gdx.files.internal("dialogue/greyBar.png"));
        rectangle = new Rectangle(position.x, position.y, size.x, size.y);

        this.answer = answer;
        this.number = number;

        sound = Gdx.audio.newSound(Gdx.files.internal("dialogue/selecting.wav"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("dialogue/selected.wav"));

    }

    public void Render(SpriteBatch spriteBatch, OrthographicCamera camera, BitmapFont font){

        Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        camera.unproject(pos);

        Rectangle touch = new Rectangle(pos.x, pos.y, 1, 1);

        if(!Gdx.input.isTouched() && pastTouch && rectangle.overlaps(touch)) {
            activated = true;
            sound2.play();
        }

        if(activated)
            font.draw(spriteBatch, Integer.toString(number), 500, 500);



        if(Gdx.input.isTouched() && rectangle.overlaps(touch)){
            spriteBatch.draw(image2, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            if(!soundActivated){ sound.play();
                Gdx.input.vibrate(200);}
            soundActivated = true;
        }



        else{
            spriteBatch.draw(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            soundActivated = false;
        }


        font.draw(spriteBatch, answer, rectangle.x + 10, rectangle.y + 65);

        pastTouch = Gdx.input.isTouched();
    }
}
