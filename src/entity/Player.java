package entity;


import entity.MapObject;
import entity.enemies.Enemy;
import tileMap.TileMap;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

    // player stuff
    private int health;
    private int maxHealth;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    private int XP;

    // attack
    private boolean attacking;
    private int attackDamage;
    private int attackRange;


    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
            2, 4, 1, 1, 4
    };

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int ATTACKING = 4;

    public Player(TileMap tm) {

        super(tm);

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.10;
        maxFallSpeed = 3.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = maxHealth = 5;

        attackDamage = 8;
        attackRange = 40;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Player/knightsprites.gif"
                    )
            );

            sprites = new ArrayList<>();
            for(int i = 0; i < numFrames.length; i++) {

                BufferedImage[] bi =
                        new BufferedImage[numFrames[i]];

                for(int j = 0; j < numFrames[i]; j++) {

                    if(i != ATTACKING) {
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * width * 2,
                                i * height,
                                width * 2,
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
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(4000);

    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setAttacking() {
        attacking = true;
    }

    private void getNextPosition() {

        // movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        else {
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        // cannot move while attacking, except in air
        if((currentAction == ATTACKING) && !(jumping || falling)) {
            dx = 0;
        }

        // jumping
        if(jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        // falling
        if(falling) {
            dy += fallSpeed;
            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //check attack has stopped
        if (currentAction == ATTACKING){
            if (animation.hasPlayedOnce()){
                attacking = false;
            }
        }

        //check done flinching
        if (flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if (elapsed > 500){
                flinching = false;
            }
        }

        // set animation
        if(attacking) {
            if(currentAction != ATTACKING) {
                currentAction = ATTACKING;
                animation.setFrames(sprites.get(ATTACKING));
                animation.setDelay(80);
                width = 60;
            }
        } else if(dy > 0) {
            if(currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(40);
                width = 30;
            }
        }
        else if(dy < 0) {
            if(currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        }
        else if(left || right) {
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(150);
                width = 30;
            }
        }
        else {
            if(currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(4000);
                width = 30;
            }
        }

        animation.update();

        // set direction
        if(currentAction != ATTACKING) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }

    }

    public void draw(Graphics2D g) {

        setMapPosition();

        // draw player
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);

    }

    public int getXP() {
        return XP;
    }

    public void checkAttack(ArrayList<Enemy> enemies) {

        for (Enemy enemy : enemies){
            if (this.intersects(enemy)){
                if (attacking){
                    enemy.hit(attackDamage);
                } else {
                    hit(enemy.getDamage());
                }
            }
        }

    }

    private void hit(int damage) {
        if (flinching) return;
        health -= damage;
        if (health <= 0){
            health = 0;
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }
}