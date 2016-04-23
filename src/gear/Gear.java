/*
 * Copyright (c) 2016.
 */

package gear;

/**
 * Represents a piece of Gear. All members of this package extend this class.
 */
public abstract class Gear {

    public enum GearType {
        WEAPON,
        HELMET,
        GLOVES,
        BOOTS,
        CHEST
    }

    protected int rating;
    protected GearType type;

    /**
     * The rating is the defense value of a piece of armor, and
     * the attack value of a weapon
     * @return the rating of this piece of gear
     */
    public int getRating() {
        return rating;
    }

}
