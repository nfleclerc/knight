package gameStates;

import entity.Player;
import entity.Player_Test;
import main.GamePanel;
import tileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by nathaniel on 2/19/16.
 */
public class Level1State extends GameState{

    private TileMap tileMap;

    private Player player;

    public Level1State(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        player = new Player(tileMap);
        player.setPosition(100, 100);
    }

    @Override
    public void update() {
        player.update();
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY()
        );
    }

    @Override
    public void draw(Graphics2D g) {

        //clear the screen
        g.setColor(new Color(10, 10, 80));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        tileMap.draw(g);
        player.draw(g);

    }

    @Override
    public void keyPressed(int k) {
        switch (k){
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJumping(true);
                break;
            case KeyEvent.VK_F:
                player.setAttacking();
        }
    }

    @Override
    public void keyReleased(int k) {
        switch (k) {
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_S:
                player.setDown(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJumping(false);
                break;
        }

    }

}
