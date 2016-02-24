package gameStates;

import entity.Explosion;
import entity.HUD;
import entity.enemies.BugBoss;
import entity.enemies.Enemy;
import entity.Player;
import entity.enemies.RoboSpider;
import entity.enemies.Wasp;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/19/16.
 */
public class ForestState extends GameState{

    private TileMap tileMap;

    private Player player;
    private ArrayList<Enemy> enemies;
    public ArrayList<Explosion> explosions;
    private HUD hud;
    private Background bg;

    public ForestState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/tilesets/grasstileset.gif");
        tileMap.loadMap("/maps/forestcave_map.tme");
        tileMap.setPosition(0, 0);

        bg = new Background("/backgrounds/forestbg.gif", 0.05);

        player = new Player(tileMap);
        player.setPosition(100, 300);

        hud = new HUD(player);

        populateEnemies();

        explosions = new ArrayList<>();



    }

    private void populateEnemies() {

        enemies = new ArrayList<>();
        java.awt.Point[] spiderPoints = new Point[] {
                new Point(510, 350),
                new Point(2000, 300),
                new Point(2076, 300),
                new Point(2368, 300),
                new Point(2614, 300),
                new Point(2900, 300)
       };

        java.awt.Point[] waspPoints = new Point[] {
                new Point(856, 230),
                new Point(1569, 290),
                new Point(1230, 200),
                new Point(3270, 140)
        };

        for (Point spiderPoint : spiderPoints) {
            RoboSpider spider = new RoboSpider(tileMap, player);
            spider.setPosition(spiderPoint.x, spiderPoint.y);
            enemies.add(spider);
        }

        for (Point waspPoint : waspPoints){
            Wasp wasp = new Wasp(tileMap, player);
            wasp.setPosition(waspPoint.x, waspPoint.y);
            enemies.add(wasp);
        }

        BugBoss bugBoss = new BugBoss(tileMap, player);
        bugBoss.setPosition(4258, 200);
        enemies.add(bugBoss);

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
            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;
            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;
            case KeyEvent.VK_UP:
                player.setUp(true);
                player.setJumping(true);
                break;
            case KeyEvent.VK_DOWN:
                player.setDown(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setAttacking();
        }
    }

    @Override
    public void keyReleased(int k) {
        switch (k) {
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
            case KeyEvent.VK_UP:
                player.setUp(false);
                player.setJumping(false);
                break;
            case KeyEvent.VK_DOWN:
                player.setDown(false);
                break;
        }

    }

}
