package entity.enemies;

import entity.Animation;
import entity.Player;
import entity.items.ItemType;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/22/16.
 */
public class Wasp extends Enemy{


    public Wasp(TileMap tm, Player player) {
        super(tm, player);

        this.dropType = ItemType.BUG_WINGS;

        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.0;
        maxFallSpeed = 0.0;

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        health = maxHealth = 2;
        xpWorth = 15;

        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/bugs/wasp.gif"
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
        animation.setDelay(45);

        right = true;
        facingRight = true;
    }

    @Override
    protected void checkForDirectionChange(){
        if (!bottomRight){
            right = false;
            left = true;
            facingRight = false;
        } else if (!bottomLeft){
            right = true;
            left = false;
            facingRight = true;
        }
    }

}
