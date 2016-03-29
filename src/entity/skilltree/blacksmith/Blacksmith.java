/*
 * Copyright (c) 2016.
 */

package entity.skilltree.blacksmith;

import entity.Player;
import entity.skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Blacksmith extends Skill {

    public Blacksmith(Player player, Blacksmith previous) {
        super(player);
        this.previous = previous;
    }

    @Override
    public void activate() {
        if (previous == null || this.previous.isActive()) {
            this.active = true;
        }
    }

}
