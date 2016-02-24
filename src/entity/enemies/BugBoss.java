package entity.enemies;

import entity.Animation;
import entity.Player;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/22/16.
 */
public class BugBoss extends Enemy {

    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
            2, 4
    };

    // animation actions
    private static final int WALKING = 0;
    private static final int ROLLING = 1;

    public BugBoss(TileMap tm, Player player) {
        super(tm, player);

        width = 90;
        height = 60;
        cWidth = 80;
        cHeight = 50;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.10;
        maxFallSpeed = 3.0;
        xpWorth = 500;

        facingRight = true;

        health = maxHealth = 30;


        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/bugs/bossbeetle.gif"
                    )
            );

            sprites = new ArrayList<>();
            for(int i = 0; i < numFrames.length; i++) {

                BufferedImage[] bi =
                        new BufferedImage[numFrames[i]];

                for(int j = 0; j < numFrames[i]; j++) {

                    if(i != ROLLING) {
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * (width - 30),
                                i * height,
                                width - 30,
                                height
                        );
                    }

                }

                sprites.add(bi);

            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        currentAction = WALKING;
        animation.setFrames(sprites.get(WALKING));
        animation.setDelay(4000);

    }
}
