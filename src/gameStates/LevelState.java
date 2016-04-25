/*
 * Copyright (c) 2016.
 */

package gameStates;

import audio.AudioPlayer;
import effects.Explosion;
import evaluator.TestBank;
import hud.HUD;
import entity.Player;
import entity.enemies.Enemy;
import entity.items.Item;
import evaluator.AttackProcessor;
import main.GamePanel;
import messages.Message;
import messages.MessageFactory;
import save.Loader;
import save.Saver;
import skilltree.SkillDisplay;
import tileMap.Background;
import tileMap.TileMap;
import effects.weather.Rain;
import effects.weather.Snow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Houses all the logic for updating or drawing a state. Right now only WorldState extends this class,
 * but in the future, any levelstate would extend this.
 * */
public abstract class LevelState extends GameState{

    protected TileMap tileMap;
    protected Background helpBg;
    protected Player player;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Item> items;
    protected ArrayList<Explosion> explosions;
    protected HUD hud;
    protected Background bg;
    protected int frames;
    protected int loadFrames;
    protected AudioPlayer bgMusic;
    protected boolean musicStarted;
    protected Rain rain;
    protected Snow snow;

    public static TestBank testBank = new TestBank();

    /**
     * Updates this game state. Only updates it while the player is living.
     * Also sets the loading screen until the state is ready
     */
    @Override
    public void update() {

        if (!player.isDead()) {

            if (frames > loadFrames && !musicStarted) {
                bgMusic.loop();
                musicStarted = true;
            }

            if (player.getX() <= 52242) {
                snow.update();
            } else {
                rain.update();
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

            if (frames > loadFrames) {
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy enemy = enemies.get(i);
                    enemy.update();
                    if (enemy.isDead()) {
                        enemies.remove(i);
                        i--;
                        explosions.add(new Explosion((int) enemy.getX(), (int) enemy.getY()));
                        if (enemy.getDropType() != null) {
                            items.add(new Item(enemy.getDropType(), enemy.getX(), enemy.getY(), tileMap));
                        }
                    }
                }
            }

            for (int i = 0; i < explosions.size(); i++) {
                Explosion explosion = explosions.get(i);
                explosion.update();
                if (explosion.shouldRemove()) {
                    explosions.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                item.update();
                if (item.wasGathered()) {
                    items.remove(i);
                    i--;
                }
            }
            player.update();

            MessageFactory.getInstance().update();

            if (player.isDead()){
                bgMusic.stop();
                MessageFactory.getInstance().createMessage("DEAD", Message.MessageType.DEATH);
            }

        }

    }

    /**
     * Draws all the components of this state to the screen
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);

        if (player.getX() <= 52242) {
            snow.draw(g);
        }else if (player.getX() >= 63264){
            rain.draw(g);
        }

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

        if (!player.isDead()) {
            player.draw(g);
        }

        MessageFactory.getInstance().draw(g);


        if (frames < loadFrames) {
            helpBg.draw(g);
        }




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
                if (!player.isDead()) {
                    MessageFactory.getInstance().flushOutput();
                    new AttackProcessor(this);
                }
                break;
            case KeyEvent.VK_K:
                if (!player.isDead()) {
                    new SkillDisplay(this.getPlayer(), this);
                }
                break;
            case KeyEvent.VK_P:
                System.out.println((player.getX() + ", " + player.getY()));
                break;
            case KeyEvent.VK_S:
                if (!player.isDead()) {
                    new Saver(player, GameStateManager.key);
                }
                break;
            case KeyEvent.VK_R:
                resumeGame();
                new Loader(gameStateManager, GameStateManager.key, bgMusic);
                break;
            case KeyEvent.VK_Q:
                resumeGame();
                gameStateManager.setState(GameStateManager.MENUSTATE);
                MessageFactory.getInstance().flush();
                break;
            case KeyEvent.VK_Y:
                player.autoLevel();
        }
    }

    public synchronized void resumeGame(){
        frames = 0;
        musicStarted = false;
        bgMusic.stop();
        MessageFactory.getInstance().flush();
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
