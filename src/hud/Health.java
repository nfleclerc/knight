/*
 * Copyright (c) 2016.
 */

package hud;

import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by nathaniel on 4/13/16.
 */
public class Health {

    private Player player;

    private ArrayList<BufferedImage> hearts;
    private int frames;

    public  Health(Player player) {
        hearts = new ArrayList<>();
        this.player = player;

        try {
            for (int i = 0; i < 50; i++) {
                hearts.add(ImageIO.read(
                        getClass().getResourceAsStream("/hud/heart.gif")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g){

        int i = 0;
        int x = 0;
        int y = 0;
        while (i < player.getHealth()){


            if (player.getHealth() == 1){
                if (frames % 3 == 0){
                    i++;
                    frames++;
                    continue;
                }
            }


            if (i <= 5){
                y = 5;
            } else if (i <= 10){
                y = 20;
            } else if (i <= 15){
                y = 35;
            } else if (i <= 20){
                y = 50;
            } else {
                y = 65;
            }

            g.drawImage(hearts.get(i), x, y, null);

            x += 15;

            if (i % 5 == 0 && i != 0){
                x = 0;
            }

            i++;

        }
        frames++;

    }

}
