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
public abstract class Item extends MapObject{

    protected final Player player;
    protected BufferedImage[] sprites;
    protected ItemType type;

    protected Item(TileMap tm, Player player) {
        super(tm);
        this.player = player;

        moveSpeed = 0;
        maxSpeed = 0;
        fallSpeed = 1;
        maxFallSpeed = 1;

        width = 20;
        height = 15;
        cWidth = 15;
        cHeight = 10;

        sprites = loadSprites();

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;

    }

    protected abstract BufferedImage[] loadSprites();

    public Item make(ItemType type){
        return type.constructor().apply(tileMap, player);
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
