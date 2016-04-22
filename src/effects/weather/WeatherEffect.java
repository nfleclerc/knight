/*
 * Copyright (c) 2016.
 */

package effects.weather;

import effects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Creates a new Weather effect
 */
public abstract class WeatherEffect {

    protected Animation animation;
    protected BufferedImage[] frames;


    /**
     * Makes an image transparent or semi-transparent
     * @param image The BufferedImage you want to make more transparent.
     * @param transparency a number from 0.0f - 1.0f. 0 is completely transparent and 1 is
     *                     opaque
     * @return the image with the now adjusted transparency
     */
    protected BufferedImage makeTransparent(BufferedImage image, float transparency) {
        BufferedImage adjustedImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = adjustedImage.createGraphics();
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
        graphics.setComposite(alphaComposite);
        graphics.drawImage(image, 0, 0, null);
        return adjustedImage;
    }


    /**
     * Updates this effect
     */
    public void update(){
        animation.update();
    }

    /**
     * Draws this effect to the screen
     * @param g The graphics of the screen
     */
    public void draw(Graphics2D g){
        g.drawImage(animation.getImage(), 0, 0, null);
    }
}
