/*
 * Copyright (c) 2016.
 */

package gear;

/**
 * Created by nathaniel on 3/28/16.
 */
public abstract class Gear {

    public enum GearType {
        WEAPON,
        SHIELD,
        HELMET,
        GLOVES,
        BOOTS,
        CHEST
    }

    protected int rating;
    protected GearType type;

    public int getRating() {
        return rating;
    }

}
