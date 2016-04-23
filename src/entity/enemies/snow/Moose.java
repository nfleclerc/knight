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

    /**
     * Creates a moose enemy
     * @param tm The tile map this enemy is placed on.
     * @param player The Player. Used when applying XP increases.
     */
    public Moose(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        /* Movement Attributes */
        moveSpeed = 0.3;
        maxSpeed = 4.0;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        /* Size Attributes */
        width = 90;
        height = 60;
        cWidth = 80;
        cHeight = 40;

        /* Gameplay Attributes */
        health = maxHealth = 30;
        xpWorth = 80;
        damage = 2;

        /* Load Sprites */
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

        /* Set Animation */
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);

        right = true;
        facingRight = true;
    }

}
