package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by sebastian.fransson on 2015-12-09.
 */
public class Dialogue {

    String[] strings;
    Texture image;
    float seconds = 0;
    float interval;
    GlyphLayout layout;
    int stringNumber = 0;
    public boolean done = false;
    
    Vector2 position;


    public Dialogue(String[] strings){
        this.strings = strings;
        image = new Texture(Gdx.files.internal("dialogue/red.jpg"));
        layout = new GlyphLayout();
        
        position = new Vector2(400, 400);
    }


    public void Render(SpriteBatch spriteBatch, BitmapFont font){

        layout.setText(font, strings[stringNumber]);//don't do this every frame! Store it as member

        float width = layout.width;// contains the width of the current set text
        float height = layout.height; // contains the height of the current set text

        font.draw(spriteBatch, strings[stringNumber], position.x - width/2,  position.y + height/2);

        interval = strings[stringNumber].length() * 0.1f;//Sets the time for how long the set of text will be shown on the screen before the timer resets


        
        
        //timer
        if(stringNumber < strings.length){
            seconds += Gdx.graphics.getDeltaTime();
        }

        if(stringNumber == strings.length)
            seconds += Gdx.graphics.getDeltaTime();

        if(seconds >= interval && stringNumber < strings.length - 1){
            seconds = 0;
            stringNumber++;
        }

        if(seconds > interval && stringNumber == strings.length - 1){
            done = true;
        }
        
        if(Gdx.input.justTouched())
        	seconds = interval;

        if(done)
            font.draw(spriteBatch, Float.toString(1231f), 400,  100);


        font.draw(spriteBatch, Float.toString(seconds), 20,  350);
        font.draw(spriteBatch, Float.toString(interval), 20,  400);
        
        //spriteBatch.draw(image, position.x, position.y);


    }

}

