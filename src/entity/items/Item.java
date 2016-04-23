/*
 * Copyright (c) 2016.
 */

package entity.items;

import effects.Animation;
import entity.MapObject;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 3/28/16.
 */
public class Item extends MapObject{

    private BufferedImage[] sprites;
    private ItemType type;
    private boolean gathered;


    /**
     * Creates a new Item
     * @param type The type of the item
     * @param x The x coordinate
     * @param y The y coordinate
     * @param tm The tile map this is placed on
     */
    public Item(ItemType type, double x, double y, TileMap tm) {
        super(tm);
        this.type = type;

        this.x = x;
        this.y = y;

        moveSpeed = 0;
        maxSpeed = 0;
        fallSpeed = 1;
        maxFallSpeed = 1;

        width = 20;
        height = 15;
        cWidth = 15;
        cHeight = 10;

        loadSprites(type.getSprites());

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;
        gathered = false;

    }

    /**
     * Loads the sprites from resource s
     * @param s The location of the sprites
     */
    private void loadSprites(String s){
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(s));
            sprites = new BufferedImage[1];
                sprites[0] = spriteSheet.getSubimage(0, 0, width, height);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }

    /**
     * Updates the item (gets the next position, checks for collisions, and updates animation)
     */
    @Override
    public void update(){
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        animation.update();
    }

    /**
     * Updates the position of the item (if falling)
     */
    private void getNextPosition() {
        if (falling) {
            dy += fallSpeed;
        }
    }

    public boolean wasGathered() {
        return gathered;
    }

    public void setGathered(boolean gathered){
        this.gathered = gathered;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(
                (int) x - 1,
                (int) y - cHeight,
                1,
                cHeight
        );
    }


    @Override
    public boolean equals(Object object) {
        return object instanceof Item && ((Item) object).type == this.type;
    }

    @Override
    public int hashCode(){
        return this.type.hashCode();
    }

}
