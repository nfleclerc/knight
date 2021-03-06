package gameStates;

import audio.AudioPlayer;
import entity.Player;
import main.GamePanel;
import messages.MessageFactory;
import save.Loader;
import tileMap.Background;
import tileMap.TileMap;
import effects.weather.Rain;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * Represents the opening menu in the game.
 */
public class MenuState extends GameState {

    private Background bg;
    private AudioPlayer bgMusic;

    private int currentChoice = 0;

    private String[] options = {
            "Continue",
            "New Game",
            "Credits",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;
    private Font font;
    private int frames;
    private int loadFrames;
    private boolean musicStarted;
    private Rain rain;

    public MenuState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        try {
            bg = new Background("/background/mountainbg.gif", 1);
            bg.setVector(-1, 0);
            titleColor = Color.RED;
            titleFont = new Font("Arial", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e){
            e.printStackTrace();
        }

        bgMusic = new AudioPlayer("/music/Left-Behind_Looping.mp3");

        loadFrames = 1;

        rain = new Rain();

        init();

    }

    @Override
    public void init() {
        musicStarted = false;
    }

    /**
     * Updates this state
     */
    @Override
    public void update() {

        if (frames > loadFrames && !musicStarted){
            bgMusic.loop();
            musicStarted = true;
        }

        bg.update();

        rain.update();

        MessageFactory.getInstance().update();

        frames++;
    }

    /**
     * Draw this state to the screen
     * @param g The graphics of the screen
     */
    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);



        //draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("A Knight of Code", GamePanel.WIDTH / 2 -
                g.getFontMetrics().stringWidth("A Knight of Code") / 2, 70);


        //draw menu
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice){
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i],
                    GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(options[i]) / 2,
                    140 + i * 15);
        }

        rain.draw(g);


        MessageFactory.getInstance().draw(g);
    }

    /**
     * Perform an action depending on the key pressed.
     * @param k The key code of the key being pressed.
     */
    @Override
    public void keyPressed(int k) {
        switch (k){
            case KeyEvent.VK_ENTER:
                select();
                break;
            case KeyEvent.VK_SPACE:
                select();
                break;
            case KeyEvent.VK_UP:
                currentChoice--;
                if (currentChoice == -1){
                    currentChoice = options.length - 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                currentChoice++;
                if (currentChoice == options.length){
                    currentChoice = 0;
                }
                break;
        }
    }

    /**
     * Selects the current menu choice and performs a new action, generally setting a new state
     */
    private void select() {
        switch (currentChoice) {
            case 0:
                new Loader(gameStateManager, gameStateManager.getKey(), bgMusic);
                break;
            case 1:

                try {
                    Files.deleteIfExists(Paths.get("AKnightOfCode/Saves/knight.save"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bgMusic.stop();
                gameStateManager.setState(GameStateManager.WORLDSTATE);
                break;
            case 2:
                gameStateManager.setState(GameStateManager.CREDITSTATE);
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void load(Player player) {

    }

    @Override
    public TileMap getTileMap() {
        return null;
    }
}
