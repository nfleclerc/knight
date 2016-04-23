/*
 * Copyright (c) 2016.
 */

package entity.enemies.cave;

import effects.Animation;
import entity.Player;
import entity.enemies.Enemy;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/14/16.
 */
public class Demon extends Enemy {

    /**
     * Creates a Demon enemy
     * @param tm The tile map this enemy is placed on.
     * @param player The Player. Used when applying XP increases.
     */
    public Demon(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        /* Movement Attributes */
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        /* Size Atributes */
        width = 60;
        height = 60;
        cWidth = 40;
        cHeight = 40;
        damage = 3;

        /* Gameplay Attributes */
        health = maxHealth = 15;
        xpWorth = 50;

        /* Load Sprites */
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/demon.gif"
                    )
            );

            sprites = new BufferedImage[4];
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

        /* Set animation */
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(200);

        right = true;
        facingRight = true;
    }
}
