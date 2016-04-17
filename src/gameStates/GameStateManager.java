package gameStates;

import entity.Player;
import gameStates.levels.*;
import main.GamePanel;
import tileMap.TileMap;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Manages the various game states in the game.
 */
public class GameStateManager {

    private ArrayList<GameState> gameStates;

    private int currentState;

    public static SecretKey key;

    /**
     * Index of the menu state in the list of game states kept by this class.
     */
    public static final int MENUSTATE = 0;

    /**
     * Index of the forest state in the list of the game states kept by this class.
     */

    public static final int WORLDSTATE = 1;

    public static final int CREDITSTATE = 2;




    private GamePanel gamePanel;

    /**
     * Creates a new game state manager and begins with the menu state.
     */
    public GameStateManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gameStates = new ArrayList<>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new WorldState(this));
        gameStates.add(new CreditState(this));
        key = new SecretKeySpec(new byte[]{127, -128, 0, 0, 0, 0, 0, 0}, "DES");


    }

    /**
     * Changes the current state to the given one and initializes it.
     * @param state The state to switch to.
     */
    public void setState(int state){
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void loadWorldState(Player player){
        currentState = WORLDSTATE;
        gameStates.get(WORLDSTATE).load(player);
    }

    /**
     * Updates the current game state.
     */
    public void update() {
        gameStates.get(currentState).update();
    }

    /**
     * Draws the current game state.
     * @param g
     */
    public void draw(java.awt.Graphics2D g){
        gameStates.get(currentState).draw(g);
    }

    /**
     * Indicates when a key has been pressed, and handles the action for the current game state.
     * @param k The key code of the key being pressed.
     */
    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }

    /**
     * Indicates when a key has been released, and handles the action for the current game state.
     * @param k The key code of the key being released.
     */
    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }

    public synchronized GamePanel getGamePanel() {
        return gamePanel;
    }

    public int getCurrentState() {
        return currentState;
    }

    public TileMap getTileMap(){
        return gameStates.get(WORLDSTATE).getTileMap();
    }

    public SecretKey getKey() {
        return key;
    }
}
