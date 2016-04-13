/*
 * Copyright (c) 2016.
 */

package gameStates.levels;

import hud.HUD;
import entity.Player;
import entity.enemies.Wasp;
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
        tileMap.loadTiles("/tilesets/alteredworldset.gif");
        tileMap.loadMap("/maps/world.map");
        tileMap.setPosition(52800, 1280);

        bg = new Background("/backgrounds/mountainbg.gif", 0.5);

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

        for (Point point : waspPoints){
            Wasp wasp = new Wasp(tileMap, player);
            wasp.setPosition(point.x, point.y);
            enemies.add(wasp);
        }

    }


}
