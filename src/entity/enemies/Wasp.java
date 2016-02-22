package entity.enemies;

import entity.Animation;
import entity.Player;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/22/16.
 */
public class Wasp extends Enemy{

    private BufferedImage[] sprites;

    public Wasp(TileMap tm, Player player) {
        super(tm, player);
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.0;
        maxFallSpeed = 0.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 2;
        damage = 1;
        xpWorth = 15;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/bugs/spider.gif"
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

    @Override
    protected void checkForDirectionChange(){
        if (!bottomRight){
            right = false;
            left = true;
            facingRight = false;
        } else if (!bottomLeft && dx == 0){
            right = true;
            left = false;
            facingRight = true;
        }
    }

}
