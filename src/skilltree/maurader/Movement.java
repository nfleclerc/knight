/*
 * Copyright (c) 2016.
 */

package skilltree.maurader;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Movement extends Skill {

    private double movementBonus;

    public Movement(Player player, double movementBonus, Movement previous, int index){
        super(player, index);
        this.previous = previous;
        this.movementBonus = movementBonus;
    }

    @Override
    public void activate() {
        if (!active)
        if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.increaseMovement(movementBonus);
        }
    }

}
