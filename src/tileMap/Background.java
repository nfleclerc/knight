
package tileMap;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
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
     * Creates a new background from a given source image
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
     * @param x sets the x position of the background
     * @param y the y position of the background is no longer taken into account.
     *          While in game, only the x direction will be looped.
     */
    public void setPosition(double x, double y){
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = 0;
    }

    /**
     * Sets the vector direction that the background is scrolling in.
     * @param dx the direction and speed in the x direction to move the background in
     * @param dy the direction and speed in the y direction to move the backgroun in
     */
    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Updates the background on the current game window.
     */
    public void update(){
        x += dx;
        y += dy;
    }

    /**
     * Draws the background to the current game window.
     * @param g the graphics of the screen
     */
    public void draw(Graphics2D g){
        g.drawImage(image, (int)x, (int)y, null);
        //if the image has gone all the way by, it should be looped around
        if (Math.abs(x) == GamePanel.WIDTH) x = 1;
        //draws the image depending on where it is moving
        if (x < 0){
            g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
        }
        if (x > 0){
            g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
        }
    }
}
