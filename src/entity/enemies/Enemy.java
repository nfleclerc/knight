package entity.enemies;

import entity.MapObject;
import tileMap.TileMap;

import java.awt.*;

/**
 * Created by nathaniel on 2/21/16.
 */
public abstract class Enemy extends MapObject {

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;
    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tm){
        super(tm);
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
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    private void getNextPosition(){

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

        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if (elapsed > 400){
                flinching = false;
            }
        }

        if (right && dx == 0){
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0){
            right = true;
            left = false;
            facingRight = true;
        }
        animation.update();

    }

    public void draw(Graphics2D g){

        //if (notOnScreen()) return;

        setMapPosition();

        super.draw(g);

    }
}
