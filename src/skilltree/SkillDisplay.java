/*
 * Copyright (c) 2016.
 */

package skilltree;

import audio.AudioPlayer;
import entity.Player;
import gameStates.LevelState;
import main.Game;
import main.GamePanel;
import main.WindowManager;
import tileMap.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Displays the skilltree to the screen
 */
public class SkillDisplay extends GamePanel implements ActionListener {

    private final SkillTree skillTree;
    private final Player player;
    private JFrame window;

    private JLabel skillpoints;
    private BufferedImage image;
    private Background bg;
    private LevelState levelState;
    private AudioPlayer skillMusic;


    /**
     * Creates a new skilltree for the given levelState and player
     * @param player the player to which the skilltree belongs
     * @param levelState the level in which the player exists
     */
    public SkillDisplay(Player player, LevelState levelState){
        //pause the rest of the game
        GamePanel.interrupted = true;
        this.levelState = levelState;
        skillMusic = new AudioPlayer("/music/Realm-of-Fantasy_Looping.mp3");
        bg = new Background("/background/skillbg.gif", 1);
        this.player = player;
        this.skillTree = player.getSkillTree();

        //set up a gridlayout
        GridLayout grid = new GridLayout(6, 3, 103, 0);
        setLayout(grid);

        //set up labels to appear over the jbuttons
        JLabel nomad = new JLabel("Nomad");
        nomad.setHorizontalAlignment(JLabel.CENTER);
        nomad.setFont(new Font("Fipps", Font.PLAIN, 20));
        nomad.setForeground(Color.BLACK);
        nomad.setBackground(new Color(0, 0, 0, 0));
        nomad.setToolTipText("Increases speed by 15% each rank.");
        
        JLabel warrior = new JLabel("Warrior");
        warrior.setHorizontalAlignment(JLabel.CENTER);
        warrior.setFont(new Font("Fipps", Font.PLAIN, 20));
        warrior.setForeground(Color.BLACK);
        warrior.setBackground(new Color(0, 0, 0, 0));
        warrior.setToolTipText("Increases attack damage, attack speed, \n and attack range by 15% each rank.");

        JLabel juggernaut = new JLabel("Juggernaut");
        juggernaut.setHorizontalAlignment(JLabel.CENTER);
        juggernaut.setFont(new Font("Fipps", Font.PLAIN, 20));
        juggernaut.setForeground(Color.BLACK);
        juggernaut.setBackground(new Color(0, 0, 0, 0));
        juggernaut.setToolTipText("Increases defense by 15% each rank.");
        
        add(nomad);
        add(warrior);
        add(juggernaut);

        //sets up the panel to tell the user how to exit
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new GridLayout(1, 1));
        JLabel exitJLabel = new JLabel("Press Any Button To Go Back");
        exitJLabel.setHorizontalAlignment(JLabel.CENTER);
        exitJLabel.setFont(new Font("Fipps", Font.PLAIN, 20));
        exitJLabel.setForeground(Color.BLACK);
        exitPanel.add(exitJLabel);
        exitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        //sets up the panel that displays the number of skillpoints
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 1));
        skillpoints = new JLabel();
        skillpoints.setText("Skill Points: " + player.getSkillPoints());
        skillpoints.setHorizontalAlignment(JLabel.CENTER);
        skillpoints.setFont(new Font("Fipps", Font.PLAIN, 30));
        skillpoints.setForeground(Color.BLACK);
        titlePanel.add(skillpoints);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        //adds all the skills in the skilltree to the display
        for (int i = 0; i < skillTree.getSkills().size(); i++) {
            Skill skill = skillTree.getSkillAt(i);
            skill.addActionListener(this);
            add(skill);
        }

        setFocusable(true);
        requestFocus();
        setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        setPreferredSize(new Dimension(
                GamePanel.WIDTH * (SCALE),
                GamePanel.HEIGHT * (SCALE) - titlePanel.getHeight() - exitPanel.getHeight() - 168
        ));
        //paints the background
        JPanel backgroundPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    bg.draw((Graphics2D) g);
                }
            }
        };
        //creates the window
        //overwrites the contentpane to be this class.
        window = new JFrame("Skill Tree") {
            @Override
            public Container getContentPane() {
                return SkillDisplay.this;
            }
        };
        //makes sure the look is the same accross OSs
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                UnsupportedLookAndFeelException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //sets up the window for display
        window.setLocation(Game.window.getX(), Game.window.getY());
        window.setSize(GamePanel.WIDTH * SCALE, GamePanel.HEIGHT * SCALE);
        window.setUndecorated(true);
        titlePanel.setOpaque(false);
        setOpaque(false);
        exitPanel.setOpaque(false);
        titlePanel.setBackground(new Color(0, 0, 0, 0));
        setBackground(new Color(0, 0, 0, 0));
        exitPanel.setBackground(new Color(0, 0, 0, 0));
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(this, BorderLayout.CENTER);
        backgroundPanel.add(exitPanel, BorderLayout.SOUTH);
        window.setContentPane(backgroundPanel);
        WindowManager.setAsSubWindow(window);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        levelState.getBgMusic().stop();
        skillMusic.loop();
        window.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        player.buySkill(skillTree.getSkillAt(Integer.parseInt(e.getActionCommand())));
        repaint();
        skillpoints.setText("Skill Points: " + player.getSkillPoints());
        requestFocus();

    }

    @Override
    public void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //negatory

    }

    /**
     * If a key is pressed, exit the screen and notify the main thread
     * @param e the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        GamePanel.interrupted = false;
        skillMusic.stop();
        synchronized (Game.panel) {
            Game.panel.notify();
        }
        levelState.getBgMusic().loop();
        window.dispose();
        WindowManager.removeSubWindow();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //more nada
    }

    /**
     * Draws the image to the screen
     */
    @Override
    public void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }

    @Override
    public int getYInset() {
        return 23;
    }
}
