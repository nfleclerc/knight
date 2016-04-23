/*
 * Copyright (c) 2016.
 */

package entity.enemies.bosses;

import effects.Animation;
import entity.Player;
import entity.enemies.Enemy;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/22/16.
 */
public class Python extends Enemy {

    /**
     * Creates the boss, Python
     * @param tm The tile map this enemy is placed on.
     * @param player The Player. Used when applying XP increases.
     */
    public Python(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        /* Movement Attributes */
        moveSpeed = 0.2;
        maxSpeed = 3.0;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        /* Size Attributes */
        width = 240;
        height = 120;
        cWidth = 240;
        cHeight = 120;

        /* Gameplay Attributes */
        health = maxHealth = 50;
        xpWorth = 100;
        damage = 5;

        /* Load Sprites */
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/python.png"
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

        /* Set Animation */
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(200);

        right = true;
        facingRight = true;
    }
}
