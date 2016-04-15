/*
 * Copyright (c) 2016.
 */

package entity.enemies;

import entity.Animation;
import entity.Player;
import entity.items.ItemType;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/14/16.
 */
public class Bat extends Enemy {

    public Bat(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.8;
        maxSpeed = 0.8;
        fallSpeed = 0.0;
        maxFallSpeed = 0.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 1;
        xpWorth = 5;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/bat.gif"
                    )
            );

            sprites = new BufferedImage[10];
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
        animation.setDelay(90);

        right = true;
        facingRight = true;
    }
}
