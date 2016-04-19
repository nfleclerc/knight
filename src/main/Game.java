package main;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
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


        window = new JFrame("A Knight of Code") {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 22));
                g.fillRect(0, 0, GamePanel.WIDTH * GamePanel.SCALE, GamePanel.WIDTH * GamePanel.SCALE);
                g.setColor(Color.WHITE);
                String text = "A Nathaniel Leclerc & Brian Sherman Production";
                g.drawString(text,
                        GamePanel.WIDTH  * GamePanel.SCALE / 2 -
                                g.getFontMetrics().stringWidth(text) / 2,
                        GamePanel.HEIGHT * GamePanel.SCALE / 2);
            }
        };
        window.addComponentListener(new SubWindowManager());
        window.setUndecorated(true);
        panel = new GamePanel();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon("res/sprites/items/bug_wing.gif").getImage());
        window.setIconImages(icons);
        window.setLocation((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 -
                        GamePanel.WIDTH  * GamePanel.SCALE / 2,
                        (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 -
                        GamePanel.HEIGHT  * GamePanel.SCALE / 2);
        window.setVisible(true);

    }


}
