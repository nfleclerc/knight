package entity.enemies.bugs;

import effects.Animation;
import entity.Player;
import entity.enemies.Enemy;
import entity.items.ItemType;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/21/16.
 */
public class RoboSpider extends Enemy {


    public RoboSpider(TileMap tm, Player player){
        super(tm, player);

        this.dropType = ItemType.BUG_LEGS;


        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 2;
        xpWorth = 10;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/bugs/spider.gif"
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
        facingRight = true;
    }

}