/*
 * Copyright (c) 2016.
 */

package weather;

import entity.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 4/17/16.
 */
public abstract class WeatherEffect {

    protected Animation animation;
    protected BufferedImage[] frames;


    protected BufferedImage makeTransparent(BufferedImage image, float transparency) {
        BufferedImage dest = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g3 = dest.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
        g3.setComposite(ac);
        g3.drawImage(image, 0, 0, null);

        return dest;
    }


    public void update(){
        animation.update();
    }

    public void draw(Graphics2D g){
        g.drawImage(animation.getImage(), 0, 0, null);
    }
}
