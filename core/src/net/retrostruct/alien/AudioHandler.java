package net.retrostruct.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by kasper.esbjornsson on 2016-04-19.
 */
public class AudioHandler {

    private Music intro;
    private Music main;
    private Sound jump;
    private Sound shoot;
    private Sound score;
    private Sound explosion;

    private boolean playIntro;
    private float volume = 1.0f;

    public void setVolume(float value) {
        volume = value;
        intro.setVolume(volume);
        main.setVolume(volume);
    }

    public AudioHandler(){

        //Load music files
        intro = Gdx.audio.newMusic(Gdx.files.internal("sound/infiniteHamstersIntro.wav"));
        main = Gdx.audio.newMusic(Gdx.files.internal("sound/infiniteHamstersMain.wav"));

        //Load sounds
        jump = Gdx.audio.newSound(Gdx.files.internal("sound/jump.wav"));
        shoot = Gdx.audio.newSound(Gdx.files.internal("sound/laser.wav"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
        score = Gdx.audio.newSound(Gdx.files.internal("sound/point.wav"));

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

                Game.LOG("AUDIO", "Infinite main loop");
            }
        });
    }

    //Play a specified sound
    public void playSound(String sound){
        if(sound == "shoot"){
            shoot.play(volume);
        }
        else if(sound == "jump"){
            jump.play(volume);
        }
        else if(sound == "explosion"){
            explosion.play(volume);
        }
        else if(sound == "score"){
            score.play(volume);
        }
    }

}
