/*
 * Copyright (c) 2016.
 */

package entity.enemies;

import entity.Animation;
import entity.Player;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/14/16.
 */
public class Warrior extends Enemy {

    public Warrior(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.4;
        maxSpeed = 0.4;
        fallSpeed = 0.8;
        maxFallSpeed = 5.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 9;
        xpWorth = 5;

        //loadSprites
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/warrior.gif"
                    )
            );

            sprites = new BufferedImage[2];
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
        animation.setDelay(400);

        right = true;
        facingRight = true;

    }
}
