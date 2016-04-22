/*
 * Copyright (c) 2016.
 */

package entity.items;

/**
 * Created by nathaniel on 3/28/16.
 */
public enum ItemType {

    BUG_WINGS("/spritesheets/items/bug_wing.gif"),
    BUG_LEGS("/spritesheets/items/bug_leg.gif");

    private String sprites;

    public String getSprites() {
        return sprites;
    }

    ItemType(String sprites) {
        this.sprites = sprites;
    }
}
