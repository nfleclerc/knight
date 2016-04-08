/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import tileMap.Background;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nathaniel on 3/29/16.
 */
public abstract class Skill extends JButton {

    protected final Player player;
    protected Skill previous;
    protected boolean active;
    private int index;
    private Background activeBg;
    private Background inactiveBg;
    private Background lockedBg;



    public Skill(Player player, int index, String activeBg, String inactiveBg, String lockedBg){
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

    public int getIndex(){
        return this.index;
    }

    public abstract void activate();

    public boolean isActive() {
        return active;
    }

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

}
