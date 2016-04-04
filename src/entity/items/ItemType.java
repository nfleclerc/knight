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

    BUG_WINGS(""),
    BUG_LEGS(""),
    BUG_CARCASS("");

    private String sprites;

    public String getSprites() {
        return sprites;
    }

    ItemType(String sprites) {
        this.sprites = sprites;
    }
}
