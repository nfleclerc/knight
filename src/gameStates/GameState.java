package gameStates;

import java.awt.*;

/**
 * Created by nathaniel on 2/18/16.
 */
public abstract class GameState {

    protected GameStateManager gameStateManager;
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

}
