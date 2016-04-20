/*
 * Copyright (c) 2016.
 */

package gameStates;

import audio.AudioPlayer;
import entity.Player;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;

/**
 * Created by nathaniel on 4/16/16.
 */
public class CreditState extends GameState {

    private static final int LINES = 70;
    private Font font;
    private double y;

    public CreditState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void init() {
        try {
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e){
            e.printStackTrace();
        }
        y = GamePanel.HEIGHT;
    }

    @Override
    public void update() {
        y -= 0.4;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.WHITE);
        drawString(g);
        if (y < -g.getFontMetrics().getHeight() * LINES){
            gameStateManager.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void keyPressed(int k) {
        getGameStateManager().setState(GameStateManager.MENUSTATE);
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

    private String getCreditReel() {
        return "A Knight of Code\n" +
                "\n" +
                "Nathaniel Leclerc & Brian Sherman\n " +
                "\n" +
                "\n" +
                "Art Design\n" +
                "\n" +
                "Tileset - Jordan Trudgett\n" +
                "Mountain Background - ansimuz\n" +
                "Loading Background - ansimuz\n" +
                "Ruby Sprites - killyoverdrive\n" +
                "Wasp Sprites - Stephen Challener and Poss\n" +
                "Spider Sprites - Stephen Challener and Poss\n" +
                "Beetle Sprites - Stephen Challener and Poss\n" +
                "Bat Sprites - Calciumtrice\n" +
                "Rat Sprites - Calciumtrice\n" +
                "Moose Sprites - Calciumtrice\n" +
                "Ghost Sprites - sonild\n" +
                "Skeleton Knight Sprites - sonild\n" +
                "Demon Sprites - Redshrike and William.Thomsonj\n" +
                "Heart Sprite - ForeignGuyMike\n" +
                "Fipps Font - pheist" +
                "Snow - Dave Sutton\n" +
                "\n" +
                "The above art has been adapted from its original\n" +
                "form. The owner of each piece does not endorse\n" +
                "its use in this project.\n" +
                "\n" +
                "Javar Sprites - Chase Corpus\n" +
                "Dragon Sprites - Chase Corpus\n" +
                "Python Sprites - Chase Corpus\n" +
                "Yeti Sprites - Chase Corpus\n" +
                "\n" +
                "All other art by Nathaniel Leclerc and Brian Sherman\n" +
                "\n" +
                "\n" +
                "Sound Design\n" +
                "\n" +
                "Music - Eric Matyas\n" +
                "Sound Effects - Mike Koenig\n" +
                "\n" +
                "\n" +
                "TileMap made with Tiled by Thorbørn Lindeijer\n" +
                "\n" +
                "Game Engine adapted from that of\n" +
                "Dragon Tale by ForeignGuyMike\n" +
                "\n" +
                "\n" +
                "All non-commissioned art and sound has been licensed\n" +
                "under the Creative Commons Attribution 3.0 License\n" +
                "which is available at\n" +
                "creativecommons.org/licenses/by/3.0/legalcode\n" +
                "\n" +
                "\n" +
                "Special Thanks\n" +
                "\n" +
                "Fred Li\n" +
                "Kristin Wilinkiewicz\n" +
                "Gavin-Rae Barnaby\n" +
                "Ella Perry\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Until Next Time";
    }

    private void drawString(Graphics g) {
        int i= 0;
        for (String line : getCreditReel().split("\n")) {
            double ypos = y + i * g.getFontMetrics().getHeight();
            if (ypos > 0)
            g.drawString(line,
                    GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(line) / 2,
                    (int)ypos);
            i++;
        }
    }

}
