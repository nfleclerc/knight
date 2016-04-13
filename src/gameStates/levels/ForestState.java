package gameStates.levels;

import hud.HUD;
import entity.enemies.BugBoss;
import entity.Player;
import entity.enemies.RoboSpider;
import entity.enemies.Wasp;
import gameStates.GameStateManager;
import hud.Health;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nathaniel on 2/19/16.
 *
 * Represents the Bugged Forest level.
 */
public class ForestState extends LevelState{

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
        player.setPosition(300, 140);

        health = new Health(player);

        populateEnemies();

        explosions = new ArrayList<>();
        items = new ArrayList<>();

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
        bugBoss.setPosition(4258, 380);
        enemies.add(bugBoss);

    }

}
