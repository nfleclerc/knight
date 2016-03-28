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

    BUG_WINGS(BugWings::new),
    BUG_LEGS(BugLegs::new),
    BUG_CARCASS(BugCarcass::new);

    BiFunction<TileMap, Player, Item> constructor;

    public BiFunction<TileMap, Player, Item> constructor() {
        return constructor;
    }

    ItemType(BiFunction<TileMap, Player, Item> constructor){
        this.constructor = constructor;
    }
}
