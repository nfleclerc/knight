/*
 * Copyright (c) 2016.
 */

package effects.weather;

import effects.Animation;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Creates a new rain background effect
 */
public class Rain extends WeatherEffect {

    public Rain(){

        int width = GamePanel.WIDTH;
        int height = GamePanel.HEIGHT;

        //read in the rain frames
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/effects/weather/rain.gif"
                    )
            );

            frames = new BufferedImage[8];
            for (int i = 0; i < frames.length; i++) {
                frames[i] = spriteSheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        //decrease the transparency of each of the frames, making them
        //appear better over the dark nighttime background
        for (int i = 0; i < frames.length; i++) {
            frames[i] = makeTransparent(frames[i], 0.2F);
        }

        animation = new Animation();
        animation.setFrames(frames);
        animation.setDelay(18);
    }



}
