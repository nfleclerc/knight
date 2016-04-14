/*
 * Copyright (c) 2016.
 */

package crafting;

import entity.Player;
import evaluator.Highlighter;
import main.Game;
import main.GamePanel;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by nathaniel on 4/7/16.
 */
public class CraftWindow extends GamePanel implements ActionListener {

    private final JFrame window;
    private final Player player;
    private BufferedImage image;
    private Graphics2D g;
    private List<JRadioButton> buttons;


    public CraftWindow(Player player) {

        GamePanel.interrupted = true;
        this.player = player;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 5, 80, 0));
        buttons = new ArrayList<>();
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton b1 = new JRadioButton();
        JRadioButton b2 = new JRadioButton();
        JRadioButton b3 = new JRadioButton();
        JRadioButton b4 = new JRadioButton();
        JRadioButton b5 = new JRadioButton();
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        buttons.add(b4);
        buttons.add(b5);
        buttonGroup.add(b1);
        buttonGroup.add(b2);
        buttonGroup.add(b3);
        buttonGroup.add(b4);
        buttonGroup.add(b5);

        buttonPanel.add(new JLabel("Gauntlets"));
        buttonPanel.add(new JLabel("Greaves"));
        buttonPanel.add(new JLabel("Breastplate"));
        buttonPanel.add(new JLabel("Helm"));
        buttonPanel.add(new JLabel("Weapon"));

        int i = 0;
        for (JRadioButton button : buttons){
            button.setEnabled(player.canCraft(i));
            buttonPanel.add(button);
            i++;
        }

        add(buttonPanel, BorderLayout.NORTH);

        window = new JFrame("The MATLAB");
        window.setLocation(Game.window.getX(), Game.window.getY() + 22);
        window.setUndecorated(true);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        window.setContentPane(this);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }


            @Override
    public void actionPerformed(ActionEvent e) {
        requestFocus();
        repaint();
    }


    @Override
    public void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //negatory
    }

    @Override
    public void keyPressed(KeyEvent e) {
        close();
    }

    private void close() {
        GamePanel.interrupted = false;
        synchronized (Game.panel) {
            Game.panel.notify();
        }
        window.dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //more nada
    }

    @Override
    public void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }


}
