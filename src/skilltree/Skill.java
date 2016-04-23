/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import tileMap.Background;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a new skill for use in the skilltree
 */
public abstract class Skill extends JButton {

    protected final Player player;
    protected Skill previous;
    protected boolean active;
    private int index;
    private Background activeBg;
    private Background inactiveBg;
    private Background lockedBg;

    /**
     * Creates a new Skill for use in the skilltree
     * @param player The player that owns this skill
     * @param index the index of this skill in the skilltree
     * @param activeBg the background to display while this skill is active
     * @param inactiveBg the background to display while this skill is
     *                   available for purchase
     * @param lockedBg the background to display while this skill is locked to
     *                 the player
     */
    public Skill(Player player, int index, String activeBg, String inactiveBg, String lockedBg){
        setContentAreaFilled(false);
        setBorderPainted(false);
        this.player = player;
        this.active = false;
        this.index = index;
        this.activeBg = new Background(activeBg, 1);
        this.inactiveBg = new Background(inactiveBg, 1);
        this.lockedBg = new Background(lockedBg, 1);

        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));

        this.setActionCommand(String.valueOf(getIndex()));

    }

    /**
     * Gets the index of this skill in the skilltree
     * @return
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * If this skill is not active and the previous is active, or
     * if it is the first in the list, when clicked this skill becomes activated
     * and applies its bonus to the player
     */
    public abstract void activate();

    /**
     * Returns whether or not this skill is active
     * @return whether or not this skill is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the previous skill in this tree
     * @return the previous skill in this tree, or null if it is the first one
     */
    public Skill getPrevious() {
        return previous;
    }

    @Override
    public void paintComponent(Graphics g){
        if (active){
            activeBg.draw((Graphics2D) g);
        } else if (previous != null && !previous.isActive()) {
            lockedBg.draw((Graphics2D) g);
        } else {
            inactiveBg.draw((Graphics2D) g);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(isActive());
    }
}
