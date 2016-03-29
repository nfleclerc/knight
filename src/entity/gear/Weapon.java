/*
 * Copyright (c) 2016.
 */

package entity.gear;

/**
 * Created by nathaniel on 3/28/16.
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
