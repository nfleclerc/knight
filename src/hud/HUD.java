package hud;

import entity.Player;

import java.awt.*;

/**
 * Houses all components of the HUD
 */
public class HUD {

    private LevelCounter levelCounter;
    private Health health;
    private XPBar bar;

    public HUD(Player player) {
        this.health = new Health(player);
        this.levelCounter = new LevelCounter(player);
        this.bar = new XPBar(player);
    }

    /**
     * Draws all members of the HUD to the screen
     * @param g the graphics of the screen
     */
    public void draw(Graphics2D g) {
        health.draw(g);
        levelCounter.draw(g);
        bar.draw(g);
    }

}
