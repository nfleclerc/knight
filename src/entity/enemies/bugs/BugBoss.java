package entity.enemies.bugs;

import effects.Animation;
import entity.Player;
import entity.enemies.Enemy;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/22/16.
 */
public class BugBoss extends Enemy {

    /* The enemy can be walking or rolling; these variables distinguish between the two states */
    private static final int WALKING = 0;
    private static final int ROLLING = 1;

    /* The time that the enemy has been rolling */
    private int rollTimer;

    /* An arraylist to store sprite sets locally (one for rolling, one for walking) */
    private ArrayList<BufferedImage[]> sprites;

    /* Frame timing for animations */
    private final int[] numFrames = {
            2, 4
    };
    private boolean walking;

    /**
     * Creates a boss-type bug enemy
     * @param tm The tile map this enemy is placed on.
     * @param player The Player. Used when applying XP increases.
     */
    public BugBoss(TileMap tm, Player player){
        super(tm, player);

        this.dropType = null;

        /* Movement Attributes */
        moveSpeed = 0.2;
        maxSpeed = 0.3;
        fallSpeed = 0.10;
        maxFallSpeed = 4.0;

        /* Size Attributes */
        width = 240;
        height = 120;
        cWidth = 230;
        cHeight = 80;
        damage = 5;

        /* Gameplay Attributes */
        health = maxHealth = 30;
        xpWorth = 500;

        /* Load Sprites */
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/bugs/bossbeetle.gif"
                    )
            );

            sprites = new ArrayList<>();
            // Incorporating animation
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

    /**
     * Updates the position, switches between rolling and walking, and checks for flinching
     */
    @Override
    public void update() {


        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        checkDoneFlinching(400);

        checkForDirectionChange();

        rollTimer++;
        if (rollTimer >= 300){
            rollTimer = 0;
            walking = !walking;
        }

        if (walking) {
            setAnimation(WALKING, 400, 240);
            maxSpeed = 0.3;
            cWidth = 160;
            cHeight = 110;
            y = 1415;
        } else {
            setAnimation(ROLLING, 100, 120);
            maxSpeed = 5;
            cWidth = 80;
            cHeight = 80;
            y = 1430;
        }


        animation.update();
    }

    /**
     * Sets the animation of the enemy
     * @param currentAction ROLLING or WALKING
     * @param delay The delay between each frame
     * @param width The width of the sprite
     */
    private void setAnimation(int currentAction, int delay, int width){
        if(this.currentAction != currentAction){
            this.currentAction = currentAction;
            animation.setFrames(sprites.get(currentAction));
            animation.setDelay(delay);
            this.width = width;
        }
    }

}
