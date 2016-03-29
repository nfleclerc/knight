/*
 * Copyright (c) 2016.
 */

package entity.skilltree.juggernaut;

import entity.Player;
import entity.skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Defense extends Skill {

    private double defenseBonus;

    public Defense(Player player, double defenseBonus, Defense previous){
        super(player);
        this.previous = previous;
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void activate() {
        if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.increaseDefense(defenseBonus);
        }
    }

}
