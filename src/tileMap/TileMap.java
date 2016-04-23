package tileMap;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Represents the Tile Map for a given level.
 */
public class TileMap {

    //position
    private double x;
    private double y;

    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;

    //map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    private BufferedImage tileSet;
    private int numTilesAcross;
    private Tile[][] tiles;

    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColToDraw;

    /**
     * Creates a new tilemap
     * @param tileSize The size of each tile in pixels. Height and Width must be the same.
     */
    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }

    /**
     * Loads a given Tile Set for a level.
     * @param s The location of the image used for the tile set.
     */
    public void loadTiles(String s){
        try {
            tileSet = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileSet.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                subimage = tileSet.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Loads a map using a given map file. Map files are text files containing the
     * location and layout of each file.
     * @param s The location of the map file.
     */
    public void loadMap(String s){

        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in));
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;

            String delims = "\\s+";

            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @return The size of the tile (Height and Width are the same).
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     *
     * @return The x position of the map.
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return The y position of the map.
     */
    public double getY() {
        return y;
    }

    /**
     *
     * @return The total width of the map.
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return The total height of the map.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the type of this tile (either NORMAL or BLOCKED).
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return The type of this tile (either NORMAL or BLOCKED).
     */
    public int getType(int row, int col){
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getType();
    }

    /**
     * Sets the curent position of the map on the screen.
     * @param x the x-coordinate of the map
     * @param y the y-coordinate of the map
     */
    public void setPosition(double x, double y){
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int)-this.x / tileSize;
        rowOffset = (int)-this.y / tileSize;
    }

    /**
     * Makes sure the map is in the bounds at all times.
     */
    private void fixBounds() {
        if (x < xmin) {x = xmin;}
        if (x > xmax) {x = xmax;}
        if (y < ymin) {y = ymin;}
        if (y > ymax) {y = ymax;}
    }

    /**
     * Draws the map to the screen.
     * @param g the graphics of the screen
     */
    public void draw(Graphics2D g){
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

            if (row >= numRows) break;

            for (int col = colOffset; col < colOffset + numColToDraw; col++) {

                if (col >= numCols) break;
                if (map[row][col] == 0) continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(tiles[r][c].getImage(),
                        (int)x + col * tileSize,
                        (int)y + row * tileSize,
                        null);
            }
        }
    }

    /**
     *
     * @return The number of rows in the map.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     *
     * @return The number of columns in the map.
     */
    public int getNumCols() {
        return numCols;
    }
}
