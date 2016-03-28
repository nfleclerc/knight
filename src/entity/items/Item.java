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

    private final Player player;
    protected BufferedImage sprite;

    public Item(TileMap tm, Player player) {
        super(tm);
        this.player = player;

        moveSpeed = 0;
        maxSpeed = 0;
        fallSpeed = 1;
        maxFallSpeed = 1;

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

}
