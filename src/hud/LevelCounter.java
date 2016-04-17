/*
 * Copyright (c) 2016.
 */

package hud;

import entity.Player;
import main.GamePanel;

import java.awt.*;

/**
 * Created by nathaniel on 4/13/16.
 */
public class LevelCounter {

    private final Player player;
    private String level;
    private final Font font;

    public LevelCounter(Player player){
        this.player = player;
        font = new Font("Arial", Font.BOLD, 12);
    }


    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(font);
        level = "Level " + player.getLevel();
        g.drawString(level,
                GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(level) / 2,
                17);
    }


}
