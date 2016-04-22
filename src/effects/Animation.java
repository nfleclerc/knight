package effects;

import java.awt.image.BufferedImage;

/**
 * All classes that are drawn to the screen use an Animation. This class
 * loops through the different frames that is passed.
 */
public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    private boolean playedOnce;

    /**
     * Creates a new Animation
     */
    public Animation(){
        playedOnce = false;
    }

    /**
     * Sets the frames that a given animation will use
     * @param frames The set of frames that the animation will loop through
     */
    public void setFrames(BufferedImage[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    /**
     * Sets a delay between each frame
     * @param delay The time to wait, before going to the next frame
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }

    /**
     * Updates the animation
     */
    public void update(){

        if (delay == -1) return;

        //waits the apropriate amount of time before moving on to the next frame
        long elapsed = (System.nanoTime() - startTime) / 1_000_000;
        if (elapsed > delay){
            //enough time has elapsed
            currentFrame++;
            startTime = System.nanoTime();
        }
        if (currentFrame == frames.length){
            //if the end of the array has been reached, go back to the beginning
            currentFrame = 0;
            playedOnce = true;
        }

    }

    /**
     * Returns the current frame in this animation
     * @return the current frame in this animation
     */
    public BufferedImage getImage(){
        return frames[currentFrame];
    }

    /**
     * Returns whether or not this animation has played through once or not
     * @return true if this animation has played once fully, and false otherwise
     */
    public boolean hasPlayedOnce() {
        return playedOnce;
    }
}
