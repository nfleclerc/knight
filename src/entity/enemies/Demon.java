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
public class Demon extends Enemy{

    public Demon(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = null;

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.8;
        maxFallSpeed = 1.8;

        width = 60;
        height = 60;
        cWidth = 40;
        cHeight = 40;

        health = maxHealth = 15;
        xpWorth = 50;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/demon.gif"
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

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(200);

        right = true;
        facingRight = true;
    }
}
