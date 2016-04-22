/*
 * Copyright (c) 2016.
 */

package entity.enemies.snow;

import effects.Animation;
import entity.Player;
import entity.enemies.Enemy;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/14/16.
 */
public class Moose extends Enemy {

    public Moose(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.3;
        maxSpeed = 4.0;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        width = 90;
        height = 60;
        cWidth = 80;
        cHeight = 40;

        health = maxHealth = 30;
        xpWorth = 80;
        damage = 2;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/moose.gif"
                    )
            );

            sprites = new BufferedImage[5];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);

        right = true;
        facingRight = true;
    }

}
