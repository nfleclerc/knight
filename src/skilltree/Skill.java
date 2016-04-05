/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;

import javax.swing.*;

/**
 * Created by nathaniel on 3/29/16.
 */
public abstract class Skill extends JButton {

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
