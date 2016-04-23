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
public class Yeti extends Enemy{

    public Yeti(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        width = 240;
        height = 240;
        cWidth = 240;
        cHeight = 240;

        health = maxHealth = 200;
        xpWorth = 200;
        damage = 10;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/yeti.png"
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
        animation.setDelay(200);

        right = true;
        facingRight = true;
    }
}