/*
 * Copyright (c) 2016.
 */

package gameStates;

import audio.AudioPlayer;
import main.GamePanel;
import tileMap.Background;

import java.awt.*;

/**
 * Created by nathaniel on 4/16/16.
 */
public class CreditState extends GameState {

    private final AudioPlayer bgMusic;
    private int loadFrames;
    private Font font;
    private double y;

    public CreditState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        try {
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e){
            e.printStackTrace();
        }

        bgMusic = new AudioPlayer("/music/Left-Behind_Looping.mp3");

        loadFrames = 1;

        y = GamePanel.HEIGHT;

    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        y -= 0.4;
        System.out.println(y);
        if (y < -657){
            gameStateManager.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(Color.WHITE);
        drawString(g);
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

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
                "Ruby Sprites - killyoverdrive\n" +
                "Wasp Sprites - Stephen Challener and Poss\n" +
                "Spider Sprites - Stephen Challener and Poss\n" +
                "Beetle Sprites - Stephen Challener and Poss\n" +
                "Bat Sprites - Calciumtrice\n" +
                "Rat Sprites - Calciumtrice\n" +
                "Moose Sprites - Calciumtrice\n" +
                "Ghost Sprites - sonild\n" +
                "Skeleton Knight - sonild\n" +
                "Demon Sprites - Redshrike and William.Thomsonj\n" +
                "Heart Sprite - ForeignGuyMike\n" +
                "Fipps Font - pheist\n" +
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
                "All art has been adapted from its original form. The owner\n" +
                "of each piece does not endorse their use in this project.\n" +
                "\n" +
                "All art and sound has been licensed under the Creative\n" +
                "Commons Attribution 3.0 License which is available at\n" +
                "creativecommons.org/licenses/by/3.0/legalcode\n" +
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
