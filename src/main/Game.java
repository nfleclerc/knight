package main;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to start the game and play it.
 */
public class Game{

    public static JFrame window;
    public static GamePanel panel;

    /**
     * Main method for the game.
     * @param args Nothing is passed into this
     */
    public static void main(String... args){


        window = new JFrame("A Knight of Code") {
            //creats the splash screen that is displayed while the main menu is loading
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
        window.addComponentListener(new WindowManager());
        window.setUndecorated(true);
        panel = new GamePanel();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon("res/spritesheets/player/ruby.gif").getImage());
        window.setIconImages(icons);
        window.setLocation((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 -
                        GamePanel.WIDTH  * GamePanel.SCALE / 2,
                        (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 -
                        GamePanel.HEIGHT  * GamePanel.SCALE / 2);
        window.setVisible(true);

    }


}
