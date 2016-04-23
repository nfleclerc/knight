/*
 * Copyright (c) 2016.
 */

package hud;

import entity.Player;
import main.GamePanel;

import java.awt.*;

/**
 * Displays the amount of progress made towards leveling up
 * */
public class XPBar {

    private Player player;

    public XPBar(Player player){
        this.player = player;
    }

    /**
     * Draws a bar at the bottom of the screen that fills up as the player collects XP.
     * When a player levels up, the bar is reset.
     * @param g the graphics to draw to
     */
    public void draw(Graphics2D g){
        int barWidth = GamePanel.WIDTH - 20;
        double ratio = (double)(player.getXP() % player.getXPRequiredForLevelUp()) /
                (double)player.getXPRequiredForLevelUp();
        g.setColor(new Color(186, 168, 36, 183));
        g.drawRect(10, GamePanel.HEIGHT - 15, barWidth, 5);
        g.fillRect(10, GamePanel.HEIGHT - 15, (int) (ratio * barWidth), 5);
    }

}
