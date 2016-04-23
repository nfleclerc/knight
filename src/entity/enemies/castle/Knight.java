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

    /**
     * Creates a knight enemy
     * @param tm The tile map this enemy is placed on.
     * @param player The Player. Used when applying XP increases.
     */
    public Knight(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        /* Movement Attributes */
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        /* Size Attributes */
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        /* Gameplay Attributes */
        health = maxHealth = 20;
        xpWorth = 10;
        damage = 2;

        /* Load Sprites */
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

        /* Set Animation */
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(200);

        right = true;
        facingRight = true;
    }
}
