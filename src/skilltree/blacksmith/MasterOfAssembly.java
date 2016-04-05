/*
 * Copyright (c) 2016.
 */

package skilltree.blacksmith;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class MasterOfAssembly extends Skill {

    public MasterOfAssembly(Player player, Blacksmith previous) {
        super(player);
        this.previous = previous;
    }

    @Override
    public void activate() {

    }

}
