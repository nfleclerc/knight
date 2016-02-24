package entity.enemies;

import entity.Animation;
import entity.Player;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/22/16.
 */
public class BugBoss extends Enemy {

    public BugBoss(TileMap tm, Player player){
        super(tm, player);
        moveSpeed = 0.3;
        maxSpeed = 0.4;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 240;
        height = 120;
        cWidth = 230;
        cHeight = 110;

        health = maxHealth = 30;
        xpWorth = 500;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/bugs/bossbeetle.gif"
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
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }
}
