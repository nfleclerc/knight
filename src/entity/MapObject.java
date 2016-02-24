package entity;

import main.GamePanel;
import tileMap.Tile;
import tileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/19/16.
 */
public abstract class MapObject {

    //tilestuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;
    protected double ymap;

    //position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    protected int width;
    protected int height;

    //collision box
    protected int cWidth;
    protected int cHeight;

    //collisions
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;
    protected double xtemp;
    protected double ytemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;


    //movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;

    //movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    protected boolean flinching;
    protected long flinchTimer;

    //constructor
    public MapObject(TileMap tm) {
        tileMap = tm;
        tileSize = tm.getTileSize();
    }

    public boolean intersects(MapObject other) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = other.getRectangle();
        return r1.intersects(r2);
    }

    public Rectangle getRectangle() {
        return new Rectangle(
                (int) x - cWidth,
                (int) y - cHeight,
                cWidth,
                cHeight
        );
    }

    public void checkTileMapCollision() {
        currCol = (int) x / tileSize;
        currRow = (int) y / tileSize;

        xdest = x + dx;
        ydest = y + dy;

        xtemp = x;
        ytemp = y;

        calculateCollisionInYDirection();
        calculateCollisionInXDirection();

        if (!falling) {
            calculateCorners(x, ydest + 1);
            if (!bottomRight && !bottomLeft) {
                falling = true;
            }
        }
    }

    private void calculateCollisionInYDirection() {
        calculateCorners(x, ydest);
        checkTopTiles();
        checkBottomTiles();
    }

    private void calculateCollisionInXDirection() {
        calculateCorners(xdest, y);
        checkLeftTiles();
        checkRightTiles();
    }

    private void checkTopTiles(){
        if (dy < 0) {
            if (topLeft || topRight) {
                dy = 0;
                ytemp = currRow * tileSize + cHeight / 2;
            } else {
                ytemp += dy;
            }
        }
    }

    private void checkBottomTiles(){
        if (dy > 0) {
            if (bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                ytemp = (currRow + 1) * tileSize - cHeight / 2;
            } else {
                ytemp += dy;
            }
        }
    }

    private void checkLeftTiles(){
        if (dx < 0) {
            if (topLeft || bottomLeft) {
                dx = 0;
                xtemp = currCol * tileSize + cWidth / 2;
            } else {
                xtemp += dx;
            }
        }
    }

    private void checkRightTiles(){
        if (dx > 0) {
            if (topRight || bottomRight) {
                dx = 0;
                xtemp = (currCol + 1) * tileSize - cWidth / 2;
            } else {
                xtemp += dx;
            }
        }
    }

    public void calculateCorners(double x, double y) {
        int leftTile = (int)(x - cWidth / 2) / tileSize;
        int rightTile = (int)(x + cWidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cHeight / 2) / tileSize;
        int bottomTile = (int)(y + cHeight / 2 - 1) / tileSize;
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }

    public double getX() {
        return (int)x;
    }

    public double getY() {
        return (int)y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getcHeight() {
        return cHeight;
    }

    public int getcWidth() {
        return cWidth;
    }

    public void setPosition(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }

    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }

    public void setLeft(boolean left){
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void checkDoneFlinching(int flinchTime){
        //check done flinching
        if (flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if (elapsed > flinchTime){
                flinching = false;
            }
        }
    }

    public boolean isOnScreen(){
        return (x + xmap + width < 0 ||
                x + xmap - width > GamePanel.WIDTH ||
                y + ymap + height < 0 ||
                y + ymap - height > GamePanel.HEIGHT);
    }

    public void draw(Graphics2D g){
        if(facingRight) {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xmap - width / 2),
                    (int)(y + ymap - height / 2),
                    null
            );
        }
        else {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xmap - width / 2 + width),
                    (int)(y + ymap - height / 2),
                    -width,
                    height,
                    null
            );

        }
    }


}
