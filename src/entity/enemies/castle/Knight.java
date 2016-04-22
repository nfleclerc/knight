/*
 * Copyright (c) 2016.
 */

package entity.enemies.castle;

import effects.Animation;
import entity.Player;
import entity.enemies.Enemy;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/14/16.
 */
public class Knight extends Enemy {

    public Knight(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 20;
        xpWorth = 10;
        damage = 2;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/warrior.gif"
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
        animation.setDelay(200);

        right = true;
        facingRight = true;
    }
}
