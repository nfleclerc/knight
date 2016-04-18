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
 * Created by nathaniel on 4/15/16.
 */
public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String s) {

        try {

            BufferedInputStream in = new BufferedInputStream(getClass().getResourceAsStream(s));
            AudioInputStream audio = AudioSystem.getAudioInputStream(in);

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

            clip = AudioSystem.getClip();
            clip.open(decodedAis);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {
        if (clip == null) return;
        stop();
        clip.start();
    }

    public void playOnce(){
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

    public void close(){
        stop();
        clip.close();
    }


    public void loop() {
        if (clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
