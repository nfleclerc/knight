/*
 * Copyright (c) 2016.
 */

package gameStates.levels;

import audio.AudioPlayer;
import entity.Explosion;
import hud.HUD;
import entity.Player;
import entity.enemies.Enemy;
import entity.items.Item;
import evaluator.AttackProcessor;
import gameStates.GameState;
import hud.Health;
import main.GamePanel;
import messages.Message;
import messages.MessageFactory;
import save.Saver;
import skilltree.SkillDisplay;
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
    protected ArrayList<Item> items;
    protected ArrayList<Explosion> explosions;
    protected HUD hud;
    protected Background bg;
    protected int frames;
    protected int loadFrames;
    protected AudioPlayer bgMusic;
    private boolean musicStarted;

    @Override
    public void update() {

        if (frames > loadFrames && !musicStarted){
            bgMusic.loop();
            musicStarted = true;
        }

        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(),
                GamePanel.HEIGHT / 2 - player.getY()
        );

        bg.setPosition(tileMap.getX(), tileMap.getY());

        if (frames > loadFrames) {
            player.checkAttack(enemies);
            player.checkGather(items);
        }

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update();
            if (enemy.isDead()){
                enemies.remove(i);
                i--;
                explosions.add(new Explosion((int)enemy.getX(), (int)enemy.getY()));
                if (enemy.getDropType() != null) {
                    items.add(new Item(enemy.getDropType(), enemy.getX(), enemy.getY(), tileMap));
                }
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

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            item.update();
            if (item.wasGathered()){
                items.remove(i);
                i--;
            }
        }
        player.update();


        MessageFactory.getInstance().update();
    }

    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);

        tileMap.draw(g);

        if (frames > loadFrames) {
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
        }

        for (Item item : items){
            item.draw(g);
        }

        for (Explosion explosion : explosions){
            explosion.setMapPostion((int)tileMap.getX(), (int)tileMap.getY());
            explosion.draw(g);
        }



        hud.draw(g);

        player.draw(g);


        if (frames < loadFrames){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
            MessageFactory.getInstance().createMessage("Loading...", Message.MessageType.LOADING);
        }



        MessageFactory.getInstance().draw(g);

        frames++;

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
                break;
            case KeyEvent.VK_K:
                new SkillDisplay(this.getPlayer(), this);
                break;
            case KeyEvent.VK_P:
                System.out.println((player.getX() + ", " + player.getY()));
                break;
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

    public AudioPlayer getBgMusic() {
        return bgMusic;
    }

    public TileMap getTileMap(){
        return tileMap;
    }
}
