/*
 * Copyright (c) 2016.
 */

package gear.weapons;

import gear.Weapon;

/**
 * Created by nathaniel on 3/28/16.
 */
public class SimpleSword extends Weapon {

    public SimpleSword() {
        super();
        this.attackRange = 15;
        this.rating = 5;
        this.attackSpeed = 60;
    }
}
