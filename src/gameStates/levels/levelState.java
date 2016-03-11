/*
 * Copyright (c) 2016.
 */

package gameStates.levels;

import entity.Explosion;
import entity.HUD;
import entity.Player;
import entity.enemies.Enemy;
import evaluator.AttackProcessor;
import evaluator.CodeWindow;
import gameStates.GameState;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by nathaniel on 3/10/16.
 */
public abstract class LevelState extends GameState{

    protected TileMap tileMap;

    protected Player player;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Explosion> explosions;
    protected HUD hud;
    protected Background bg;

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
                new AttackProcessor(this);
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

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

}