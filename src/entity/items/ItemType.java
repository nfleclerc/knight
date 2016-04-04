/*
 * Copyright (c) 2016.
 */

package entity.items;

import entity.Player;
import tileMap.TileMap;

import java.util.function.BiFunction;

/**
 * Created by nathaniel on 3/28/16.
 */
public enum ItemType {

    BUG_WINGS("/sprites/items/bug_wing.gif"),
    BUG_LEGS("/sprites/items/bug_leg.gif");

    private String sprites;

    public String getSprites() {
        return sprites;
    }

    ItemType(String sprites) {
        this.sprites = sprites;
    }
}
