package main;

import javax.swing.*;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Class to start the game and play it.
 */
public class Game {

    public static JFrame window;
    public static GamePanel panel;

    /**
     * Main method for the game.
     * @param args
     */
    public static void main(String... args){

        window = new JFrame("A Knight of Code");
        panel = new GamePanel();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

    public static void setPanel(JPanel panel){
        window.setContentPane(panel);
    }
}
