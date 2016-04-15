/*
 * Copyright (c) 2016.
 */

package gameStates.levels;

import entity.enemies.*;
import hud.HUD;
import entity.Player;
import gameStates.GameStateManager;
import hud.Health;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathaniel on 4/12/16.
 */
public class WorldState extends LevelState {


    public WorldState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/tilesets/worldset_withoutramps.gif");
        tileMap.loadMap("/maps/worldwithoutramps.map");
        tileMap.setPosition(0, 0);

        bg = new Background("/backgrounds/mountainbg.gif", 0.1);

        player = new Player(tileMap);
        player.setPosition(52800, 1280);

        hud = new HUD(player);

        populateEnemies();

        explosions = new ArrayList<>();
        items = new ArrayList<>();

    }

    private void populateEnemies() {
        enemies = new ArrayList<>();

        List<Point> waspPoints = new ArrayList<>();
        waspPoints.add(new Point(52907, 1250));
        waspPoints.add(new Point(54339, 1220));
        waspPoints.add(new Point(54181, 1190));
        waspPoints.add(new Point(54125, 1190));
        waspPoints.add(new Point(53852, 1160));
        waspPoints.add(new Point(53602, 1160));
        waspPoints.add(new Point(53354, 1190));
        waspPoints.add(new Point(53102, 1220));

        for (Point point : waspPoints){
            Warrior wasp = new Warrior(tileMap, player);
            wasp.setPosition(point.x, point.y);
            enemies.add(wasp);
        }

    }


}
