/*
 * Copyright (c) 2016.
 */

package gear;

/**
 *
 * Creates a new weapon. Houses logic for attack speed and attack damage
 *
 */
public abstract class Weapon extends Gear{

    protected int attackRange;
    protected int attackSpeed;

    public int getAttackRange() {
        return attackRange;
    }

    public Weapon(){
        this.type = GearType.WEAPON;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }
}
