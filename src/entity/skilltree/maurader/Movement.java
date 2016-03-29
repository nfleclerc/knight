/*
 * Copyright (c) 2016.
 */

package entity.skilltree.maurader;

import entity.Player;
import entity.skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Movement extends Skill {

    private double movementBonus;

    public Movement(Player player, double movementBonus, Movement previous){
        super(player);
        this.previous = previous;
        this.movementBonus = movementBonus;
    }

    @Override
    public void activate() {
        if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.increaseMovement(movementBonus);
        }
    }

}
