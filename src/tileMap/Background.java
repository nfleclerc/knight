
package tileMap;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Provide an automatically scrolling background to any in-game window.
 *
 */
public class Background {

    private BufferedImage image;

    private double x;
    private double y;
    private double dx;
    private double dy;

    private double moveScale;

    /**
     *
     * @param s The location of the image to make the background from.
     * @param ms The rate at which the background will automatically scroll.
     */
    public Background(String s, double ms){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale = ms;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the position of background while scrolling.
     * @param x
     * @param y
     */
    public void setPosition(double x, double y){
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = 0;
    }

    /**
     * Sets the vector direction that the background is scrolling in.
     * @param dx
     * @param dy
     */
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Updates the background on the current game window.
     */
    public void update(){
        x += dx % GamePanel.WIDTH;
        y += dy % GamePanel.HEIGHT;
    }

    /**
     * Draws the background to the current game window.
     * @param g
     */
    public void draw(Graphics2D g){
        g.drawImage(image, (int)x, (int)y, null);
        if (x < 0){
            g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
        }
        if (x > 0){
            g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
        }
    }
}
