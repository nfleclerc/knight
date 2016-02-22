package gameStates;

import entity.Explosion;
import entity.HUD;
import entity.enemies.Enemy;
import entity.Player;
import entity.enemies.RoboSpider;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/19/16.
 */
public class Level1State extends GameState{

    private TileMap tileMap;

    private Player player;
    private ArrayList<Enemy> enemies;
    public ArrayList<Explosion> explosions;
    private HUD hud;
    private Background bg;

    public Level1State(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/forestcave_map.tme");
        tileMap.setPosition(0, 0);

        bg = new Background("/Backgrounds/forestbg.gif", 0.05);

        player = new Player(tileMap);
        player.setPosition(100, 300);

        hud = new HUD(player);

        populateEnemies();

        explosions = new ArrayList<>();



    }

    private void populateEnemies() {

        enemies = new ArrayList<>();
        java.awt.Point[] points = new Point[] {
                new Point(510, 350),
                new Point(800, 50),
                new Point(2000, 250),
                new Point(3000, 150),
                new Point(4000, 150)
        };

        for (int i = 0; i < points.length; i++) {
            RoboSpider spider = new RoboSpider(tileMap);
            spider.setPosition(points[i].x, points[i].y);
            enemies.add(spider);
        }

    }

    @Override
    public void update() {
        player.update();
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY()
        );

        bg.setPosition(tileMap.getX(), tileMap.getY());

        player.checkAttack(enemies);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (enemy.isDead()){
                enemies.remove(i);
                i--;
                explosions.add(new Explosion((int)enemy.getX(), (int)enemy.getY()));
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            explosion.update();
            if (explosion.shouldRemove()){
                explosions.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {

       bg.draw(g);

        tileMap.draw(g);
        player.draw(g);

        for (Enemy enemy : enemies){
            enemy.draw(g);
        }

        for (Explosion explosion : explosions){
            explosion.setMapPostion((int)tileMap.getX(), (int)tileMap.getY());
            explosion.draw(g);
        }

        hud.draw(g);


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
