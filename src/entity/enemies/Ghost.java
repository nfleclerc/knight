/*
 * Copyright (c) 2016.
 */

package entity.enemies;

import effects.Animation;
import entity.Player;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/14/16.
 */
public class Ghost extends Enemy{

    public Ghost(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.0;
        maxFallSpeed = 0.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 5;
        xpWorth = 20;
        damage = 2;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/ghost.gif"
                    )
            );

            sprites = new BufferedImage[3];
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
        animation.setDelay(300);

        right = true;
        facingRight = false;
    }
}
