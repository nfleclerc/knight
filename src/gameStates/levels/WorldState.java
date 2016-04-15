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


    public WorldState(GameStateManager gameStateManager) {
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
        waspPoints.add(new Point(54645, 1370));
        waspPoints.add(new Point(55143, 1340));
        waspPoints.add(new Point(55365, 1190));
        waspPoints.add(new Point(55638, 1100));
        waspPoints.add(new Point(56147, 1160));
        waspPoints.add(new Point(56511, 1250));
        waspPoints.add(new Point(56543, 1250));
        waspPoints.add(new Point(56967, 1250));
        waspPoints.add(new Point(55573, 1100));
        waspPoints.add(new Point(56848, 1250));
        waspPoints.add(new Point(57333, 1250));
        waspPoints.add(new Point(57650, 1220));
        waspPoints.add(new Point(57833, 1130));
        waspPoints.add(new Point(58127, 1130));
        waspPoints.add(new Point(58420, 1190));
        waspPoints.add(new Point(58716, 1160));
        waspPoints.add(new Point(59059, 1160));
        waspPoints.add(new Point(59629, 1100));
        waspPoints.add(new Point(60086, 1220));
        waspPoints.add(new Point(60312, 1190));
        waspPoints.add(new Point(60554, 1250));
        waspPoints.add(new Point(60834, 1220));
        waspPoints.add(new Point(61104, 1160));
        waspPoints.add(new Point(61423, 1190));
        waspPoints.add(new Point(62085, 1190));
        waspPoints.add(new Point(69805, 1220));
        waspPoints.add(new Point(70265, 1130));
        waspPoints.add(new Point(70690, 1100));
        waspPoints.add(new Point(74162, 1190));
        waspPoints.add(new Point(73379, 1310));
        waspPoints.add(new Point(74165, 1310));
        waspPoints.add(new Point(74445, 1160));
        waspPoints.add(new Point(74716, 1130));


        for (Point point : waspPoints) {
            Wasp wasp = new Wasp(tileMap, player);
            wasp.setPosition(point.x, point.y);
            enemies.add(wasp);
        }

    }


}
