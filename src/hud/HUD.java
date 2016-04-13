package hud;

import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/21/16.
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

    public void draw(Graphics2D g) {
        health.draw(g);
        levelCounter.draw(g);
        bar.draw(g);
    }

}
