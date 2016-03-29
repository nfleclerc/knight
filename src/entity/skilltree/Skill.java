/*
 * Copyright (c) 2016.
 */

package entity.skilltree;

import entity.Player;

/**
 * Created by nathaniel on 3/29/16.
 */
public abstract class Skill {

    protected final Player player;
    protected Skill previous;
    protected boolean active;

    public Skill(Player player){
        this.player = player;
        this.active = false;
    }

    public abstract void activate();

    public boolean isActive() {
        return active;
    }
}
