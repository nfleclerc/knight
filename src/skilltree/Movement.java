/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Movement extends Skill {

    private double movementBonus;

    public Movement(Player player, double movementBonus, Movement previous, int index,  String activeBg, String inactiveBg, String lockedBg){
        super(player, index, activeBg, inactiveBg, lockedBg);
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
