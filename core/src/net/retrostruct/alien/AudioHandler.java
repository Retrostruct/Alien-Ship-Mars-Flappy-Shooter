package net.retrostruct.alien;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by kasper.esbjornsson on 2016-04-19.
 */
public class AudioHandler {

    private Music intro;
    private Music main;
    private Sound jump;
    private Sound shoot;
    private Sound explosion;

    private boolean playIntro;

    public AudioHandler(){

        //Load music files
        intro = Gdx.audio.newMusic(Gdx.files.internal("sound/infiniteHamstersIntro.wav"));
        main = Gdx.audio.newMusic(Gdx.files.internal("sound/infiniteHamstersmain.wav"));

        //Load sounds
        jump = Gdx.audio.newSound(Gdx.files.internal("sound/jump.wav"));
        shoot = Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));

        playIntro = true;
    }

    //Plays the main theme (infinite hamsters) correctly
    public void playMusic(){

        //Play the intro only if it's the first time
        if(playIntro){
            intro.play();
        }

        //If the intro is finished playing, play the main song
        intro.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                playIntro = false;
                main.play();
                main.setLooping(true);
            }
        });
    }

    //Play a specified sound
    public void playSound(String sound){
        if(sound == "shoot"){
            shoot.play();
        }
        else if(sound == "jump"){
            jump.play();
        }
        else if(sound == "explosion"){
            explosion.play();
        }
    }

}
