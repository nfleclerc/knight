package main;

import javax.swing.*;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Class to start the game and play it.
 */
public class Game {

    /**
     * Main method for the game.
     * @param args
     */
    public static void main(String... args){

        JFrame window = new JFrame("A Knight of Code");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }
}
