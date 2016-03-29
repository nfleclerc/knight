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

    private static final int WALKING = 0;
    private static final int ROLLING = 1;

    private int rollTimer;

    private ArrayList<BufferedImage[]> sprites;

    private final int[] numFrames = {
            2, 4
    };
    private boolean walking;

    public BugBoss(TileMap tm, Player player){
        super(tm, player);
        moveSpeed = 0.2;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 240;
        height = 120;
        cWidth = 230;
        cHeight = 80;

        health = maxHealth = 30;
        xpWorth = 500;



        //loadSprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/enemies/bugs/bossbeetle.gif"
                    )
            );

            sprites = new ArrayList<>();
            for (int i = 0; i < numFrames.length; i++) {

                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {

                    if (i != ROLLING) {
                        bi[j] = spriteSheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height
                        );
                    } else {
                        bi[j] = spriteSheet.getSubimage(
                                j * (width / 2),
                                i * height,
                                width / 2,
                                height
                        );
                    }
                }

                sprites.add(bi);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites.get(WALKING));
        animation.setDelay(400);
        walking = false;
        right = true;
        facingRight = true;
    }

    @Override
    public void update() {

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        checkDoneFlinching(400);

        checkForDirectionChange();

        rollTimer++;
        if (rollTimer >= 100){
            rollTimer = 0;
            walking = !walking;
        }


        if (walking) {
            setAnimation(WALKING, 400, 240);
            maxSpeed = 0.3;
            cWidth = 160;
            cHeight = 110;
            y = 365;
        } else {
            setAnimation(ROLLING, 100, 120);
            maxSpeed = 5;
            cWidth = 80;
            cHeight = 80;
            y = 380;
        }


        animation.update();
    }

    private void setAnimation(int currentAction, int delay, int width){
        if(this.currentAction != currentAction){
            this.currentAction = currentAction;
            animation.setFrames(sprites.get(currentAction));
            animation.setDelay(delay);
            this.width = width;
        }
    }

}
