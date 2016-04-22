/*
 * Copyright (c) 2016.
 */

package effects.weather;

import effects.Animation;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Creates a new snow effect
 */
public class Snow  extends WeatherEffect{

        public Snow(){

            int width = GamePanel.WIDTH;
            int height = GamePanel.HEIGHT;

            //reads in the snow frames
            try {
                BufferedImage spriteSheet = ImageIO.read(
                        getClass().getResourceAsStream(
                                "/effects/weather/snow.gif"
                        )
                );

                frames = new BufferedImage[20];
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

            //reduces the transparency of each of the frames to look better
            //in a nighttime setting
            for (int i = 0; i < frames.length; i++) {
                frames[i] = makeTransparent(frames[i], 0.6F);
            }

            animation = new Animation();
            animation.setFrames(frames);
            animation.setDelay(70);
        }

}
