/*
 * Copyright (c) 2016.
 */

package hud;

import entity.Player;
import main.Game;
import main.GamePanel;

import java.awt.*;

/**
 * Created by nathaniel on 4/13/16.
 */
public class XPBar {

    private Player player;

    public XPBar(Player player){
        this.player = player;
    }

    public void draw(Graphics2D g){

        int barWidth = GamePanel.WIDTH - 20;

        double ratio = (double)(player.getXP() % 100) / 100.0;

        g.setColor(new Color(186, 168, 36, 183));
        g.drawRect(10, GamePanel.HEIGHT - 15, barWidth, 5);
        g.fillRect(10, GamePanel.HEIGHT - 15, (int) (ratio * barWidth), 5);
    }

}
