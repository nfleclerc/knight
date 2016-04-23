package entity.enemies;

import entity.MapObject;
import entity.Player;
import entity.items.ItemType;
import tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/21/16.
 */
public abstract class Enemy extends MapObject {

    //The player. Used when awarding XP
    private final Player player;

    //The sprites used
    protected BufferedImage[] sprites;

    //Gameplay attributes
    protected int health;           //Health of the enemy
    protected int maxHealth;        //Maximum health of the enemy
    protected boolean dead;         //True if the enemy is dead, false if they are alive
    protected int damage;           //Damage the enemy deals through attacking
    protected boolean flinching;
    protected long flinchTimer;
    protected int xpWorth;
    protected ItemType dropType;    //Item dropped by the enemy (unused)

    /**
     * Creates an enemy
     * @param tm The tile map this enemy is placed on.
     * @param player The Player. Used when applying XP increases.
     */
    public Enemy(TileMap tm, Player player){
        super(tm);
        this.player = player;
        damage = 1;
    }

    public boolean isDead() {
        return dead;
    }

    /**
     * Gets the damage dealt by the enemy
     * @return The damage dealt BY the enemy
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Called when the enemy is hit by an attack
     * @param damage The damage dealt TO the enemy
     */
    public void hit(int damage){
        if (dead || flinching) return;
        health -= damage;
        if (health <= 0){
            health = 0;
            dead = true;
            player.gainXP(xpWorth);
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    /**
     * Updates the position of the enemy
     */
    protected void getNextPosition(){

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

        if (falling) {
            dy += fallSpeed;
        }

    }

    /**
     * Updates the enemy (gets the next position, checks for collisions, and updates animation)
     */
    public void update(){

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        checkForDirectionChange();

        animation.update();

    }

    /**
     * Checks if the enemy's direction needs to change
     */
    protected void checkForDirectionChange(){
        if (!bottomRight || (right && dx == 0)){
            dx = 0;
            right = false;
            left = true;
            facingRight = false;
        } else if (!bottomLeft || (left && dx == 0)){
            dx = 0;
            right = true;
            left = false;
            facingRight = true;
        }
    }

    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }

    public ItemType getDropType() {
        return dropType;
    }


}
