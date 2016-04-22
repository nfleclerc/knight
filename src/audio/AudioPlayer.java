/*
 * Copyright (c) 2016.
 */

package audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;

/**
 * Plays a single clip of audio. Can either be a music piece that loops,
 * or a sound effect that only plays once.
 */
public class AudioPlayer {

    //the actual audio
    private Clip clip;

    public AudioPlayer(String s) {

        try {

            //reads in the given audio
            BufferedInputStream in = new BufferedInputStream(getClass().getResourceAsStream(s));
            AudioInputStream audio = AudioSystem.getAudioInputStream(in);

            //converts the audio to signed PCM
            AudioFormat baseFormat = audio.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedAis = AudioSystem.getAudioInputStream(
                    decodeFormat, audio);

            //takes the signed PCM and makes a clip out of it
            clip = AudioSystem.getClip();
            clip.open(decodedAis);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Plays the clip once. Used for sound effects.
     */
    public void playOnce(){
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Stops any clip if it is running.
     */
    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

    /**
     * Tells a clip to keep playing indefinitely.
     */
    public void loop() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
