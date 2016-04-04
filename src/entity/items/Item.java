/*
 * Copyright (c) 2016.
 */

package entity.items;

import entity.Animation;
import entity.MapObject;
import entity.Player;
import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 3/28/16.
 */
public class Item extends MapObject{

    protected BufferedImage[] sprites;
    protected ItemType type;

    protected Item(ItemType type, TileMap tm) {
        super(tm);
        this.type = type;

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

    }

    private void loadSprites(String s){
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(s));
            sprites = new BufferedImage[1];
                sprites[1] = spriteSheet.getSubimage(0, 0, width, height);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void draw(Graphics2D g){
        setMapPosition();
        super.draw(g);
    }

    @Override
    public void update(){
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        animation.update();
    }

    private void getNextPosition() {
        if (falling) {
            dy += fallSpeed;
        }
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
