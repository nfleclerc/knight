/*
 * Copyright (c) 2016.
 */

package skilltree.blacksmith;

import entity.Player;
import skilltree.Skill;

/**
 * Created by nathaniel on 3/29/16.
 */
public class Blacksmith extends Skill {

    private final int craftIndex;

    public Blacksmith(Player player, Blacksmith previous, int index,
                      int craftIndex, String activeBg, String inactiveBg, String lockedBg){
        super(player, index, activeBg, inactiveBg, lockedBg);
        this.previous = previous;
        this.craftIndex = craftIndex;
    }

    @Override
    public void activate() {
        if (!active)
        if (previous == null || this.previous.isActive()) {
            this.active = true;
            player.setCraftingEnabled(craftIndex);
        }
    }

}
