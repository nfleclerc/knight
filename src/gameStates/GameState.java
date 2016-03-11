package gameStates;

import main.GamePanel;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Abstract class representing a game state.
 */
public abstract class GameState {

    protected GameStateManager gameStateManager;

    /**
     * Initializes this game state.
     */
    public abstract void init();

    /**
     * Updates this game state.
     */
    public abstract void update();

    /**
     * Draws this game state.
     * @param g
     */
    public abstract void draw(java.awt.Graphics2D g);

    /**
     * Indicates when a key has been pressed, and handles the action for the current game state.
     * @param k The key code of the key being pressed.
     */
    public abstract void keyPressed(int k);

    /**
     * Indicates when a key has been released, and handles the action for the current game state.
     * @param k The key code of the key being released.
     */
    public abstract void keyReleased(int k);

    public synchronized GamePanel getGamePanel() {
        return gameStateManager.getGamePanel();
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }
}
