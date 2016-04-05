/*
 * Copyright (c) 2016.
 */

package skilltree.maurader;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class AngelOfCode extends Skill {

    public AngelOfCode(Player player, Movement previous) {
        super(player);
        this.previous = previous;
    }

    @Override
    public void activate() {

    }
}
