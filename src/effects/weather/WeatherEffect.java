/*
 * Copyright (c) 2016.
 */

package effects.weather;

import effects.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/17/16.
 */
public abstract class WeatherEffect {

    protected Animation animation;
    protected BufferedImage[] frames;


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


    public void update(){
        animation.update();
    }

    public void draw(Graphics2D g){
        g.drawImage(animation.getImage(), 0, 0, null);
    }
}
