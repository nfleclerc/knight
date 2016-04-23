package tileMap;

import java.awt.image.BufferedImage;

/**
 * Represents a single tile in a map.
 */
public class Tile {

    private BufferedImage image;
    private int type;

    /**
     * Index of unblocked tiles on the TileMap.
     */
    public static final int NORMAL = 0;

    /**
     * Index of blocked tiles on the TileMap.
     */
    public static final int BLOCKED = 1;

    /**
     *
     * @param bufferedImage The image this tile contains.
     * @param type The type of this tile (either NORMAL or BLOCKED).
     */
    public Tile(BufferedImage bufferedImage, int type) {
        this.image = bufferedImage;
        this.type = type;

    }

    /**
     *
     * @return The image that this tile contains.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     *
     * @return The type of this tile (either NORMAL or BLOCKED).
     */
    public int getType() {
        return type;
    }
}
