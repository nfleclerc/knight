package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Class to start the game and play it.
 */
public class Game{

    public static JFrame window;
    public static GamePanel panel;

    /**
     * Main method for the game.
     * @param args
     */
    public static void main(String... args){

        window = new JFrame("A Knight of Code");
        window.addComponentListener(new SubWindowManager());
        panel = new GamePanel();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon("res/sprites/items/bug_wing.gif").getImage());
        window.setIconImages(icons);
        window.setVisible(true);

    }


    public static void setPanel(JPanel panel){
        window.setContentPane(panel);
    }

}
