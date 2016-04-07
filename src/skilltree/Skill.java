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

    public Skill(Player player, int index){
        this.player = player;
        this.active = false;
        this.index = index;

        this.setOpaque(true);

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
            setBackground(new Color(121, 0, 52, 255));
        } else {
            setBackground(new Color(26, 200, 0, 255));
        }
        super.paintComponent(g);
    }

    public void checkForRepaint(Graphics2D g) {
        if (active){
            paintComponent(g);
        }
    }

}
