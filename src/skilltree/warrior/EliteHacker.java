/*
 * Copyright (c) 2016.
 */

package skilltree.warrior;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class EliteHacker extends Skill {


    public EliteHacker(Player player, Attack previous) {
        super(player);
        this.previous = previous;
    }

    @Override
    public void activate() {

    }

}
