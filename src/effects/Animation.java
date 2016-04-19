package effects;

import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/20/16.
 */
public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    private boolean playedOnce;

    public Animation(){
        playedOnce = false;
    }

    public void setFrames(BufferedImage[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setFrame(int i){
        currentFrame = i;
    }

    public void update(){

        if (delay == -1) return;

        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        if (elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }

    }

    public int getFrame() {
        return currentFrame;
    }

    public BufferedImage getImage(){
        return frames[currentFrame];
    }

    public boolean hasPlayedOnce() {
        return playedOnce;
    }
}