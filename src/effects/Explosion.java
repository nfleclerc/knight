package effects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Creates a new explosion to play when an enemy is downed
 */
public class Explosion {

    private int x;
    private int y;
    private int xmap;
    private int ymap;

    private int width;
    private int height;

    private Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    /**
     * Create an explosion at the given coordinates
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Explosion(int x, int y){
        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        //read in the sprites
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/enemies/explosion.gif"
                    )
            );

            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }

    /**
     * Updates the animation. If it has played once, it should be removed
     */
    public void update(){
        animation.update();
        if(animation.hasPlayedOnce()){
            remove = true;
        }
    }

    /**
     * Returns whether or not this explosion should be removed or not
     * @return whether or not this explosion should be removed or not
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Sets the map location of this explosion
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setMapPostion(int x, int y){
        xmap = x;
        ymap = y;
    }

    /**
     * Draws this explosion
     * @param g the graphics of the screen
     */
    public void draw(Graphics2D g){
        g.drawImage(animation.getImage(),
                x + xmap - width / 2,
                y + ymap - height / 2,
                null
                );
    }


}
