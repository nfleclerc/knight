/*
 * Copyright (c) 2016.
 */

package skilltree.juggernaut;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Defense extends Skill {

    private double defenseBonus;

    public Defense(Player player, double defenseBonus, Defense previous, int index, String activeBg, String inactiveBg){
        super(player, index, activeBg, inactiveBg);
        this.previous = previous;
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void activate() {
        if (!active)
            if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.increaseDefense(defenseBonus);
        }
    }

}
