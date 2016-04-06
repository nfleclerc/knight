/*
 * Copyright (c) 2016.
 */

package skilltree.warrior;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Attack extends Skill{


    private final double attackBonus;

    public Attack(Player player, double attackBonus, Attack previous, int index){
        super(player, index);
        this.previous = previous;
        this.attackBonus = attackBonus;
    }

    @Override
    public void activate() {
        if (!active)
            if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.increaseAttack(attackBonus);
        }
    }
    
}
