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

    private final Player player;

    protected BufferedImage[] sprites;

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    protected boolean flinching;
    protected long flinchTimer;
    protected int xpWorth;
    protected ItemType dropType;

    public Enemy(TileMap tm, Player player){
        super(tm);
        this.player = player;
        damage = 1;
    }

    public boolean isDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

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

    public void update(){

        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        checkForDirectionChange();

        animation.update();

    }

    protected void checkForDirectionChange(){
        if (right && dx == 0){
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0){
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
