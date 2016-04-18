/*
 * Copyright (c) 2016.
 */

package weather;

import entity.Animation;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/17/16.
 */
public class Snow  extends WeatherEffect{

        public Snow(){

            int width = GamePanel.WIDTH;
            int height = GamePanel.HEIGHT;

            try {
                BufferedImage spriteSheet = ImageIO.read(
                        getClass().getResourceAsStream(
                                "/weather/snow.gif"
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

            for (int i = 0; i < frames.length; i++) {
                frames[i] = makeTransparent(frames[i], 0.6F);
            }

            animation = new Animation();
            animation.setFrames(frames);
            animation.setDelay(70);
        }

}
