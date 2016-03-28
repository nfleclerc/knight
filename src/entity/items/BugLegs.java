/*
 * Copyright (c) 2016.
 */

package entity.items;

import entity.Player;
import entity.items.Item;
import tileMap.TileMap;

import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 3/28/16.
 */
public class BugLegs extends Item {
    protected BugLegs(TileMap tm, Player player) {
        super(tm, player);
    }

    @Override
    protected BufferedImage[] loadSprites() {
        return new BufferedImage[0];
    }
}
