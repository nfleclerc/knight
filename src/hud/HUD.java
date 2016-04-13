package hud;

import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/21/16.
 */
public class HUD {

    private final LevelCounter levelCounter;
    private Player player;
    private Health health;

    public HUD(Player player) {
        this.health = new Health(player);
        this.levelCounter = new LevelCounter(player);

    }

    public void draw(Graphics2D g) {
        health.draw(g);
        levelCounter.draw(g);
    }

}
