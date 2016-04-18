package net.retrostruct.alien;

/**
 * Created by s on 4/15/16.
 */
public class Timer {

    float time, interval;

    public Timer(float interval) {
        this.interval = interval;
    }

    public boolean tick(float delta) {
        time += delta;
        if(time >= interval) {
            time -= interval;
            return true;
        }

        return false;
    }
}
