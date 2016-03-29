/*
 * Copyright (c) 2016.
 */

package entity.skilltree.warrior;

import entity.Player;
import entity.skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Attack extends Skill{


    private final double attackBonus;

    public Attack(Player player, double attackBonus, Attack previous){
        super(player);
        this.previous = previous;
        this.attackBonus = attackBonus;
    }

    @Override
    public void activate() {
        if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.increaseAttack(attackBonus);
        }
    }
    
}
