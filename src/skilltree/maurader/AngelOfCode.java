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

    public AngelOfCode(Player player, Movement previous, int index, String activeBg, String inactiveBg){
        super(player, index, activeBg, inactiveBg);
        this.previous = previous;
    }

    @Override
    public void activate() {
        if (!active){
            if (previous == null || this.previous.isActive()) {
                this.active = true;
            }
        }
    }
}
