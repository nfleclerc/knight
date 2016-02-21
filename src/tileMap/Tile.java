package tileMap;

import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/19/16.
 */
public class Tile {

    private BufferedImage image;
    private int type;

    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    public Tile(BufferedImage bufferedImage, int type) {
        this.image = bufferedImage;
        this.type = type;

    }

    public BufferedImage getImage() {
        return image;
    }

    public int getType() {
        return type;
    }
}
