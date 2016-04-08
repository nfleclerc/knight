/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import gameStates.GameStateManager;
import main.Game;
import main.GamePanel;
import tileMap.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by nathaniel on 4/4/16.
 */
public class SkillDisplay extends GamePanel implements ActionListener {

    private final SkillTree skillTree;
    private final Player player;
    private JFrame window;

    private Graphics2D g;
    private JLabel skillpoints;
    private BufferedImage image;
    private Background bg;


    public SkillDisplay(Player player){
        GamePanel.interrupted = true;
        bg = new Background("/backgrounds/skillbg.gif", 1);

        this.player = player;
        this.skillTree = player.getSkillTree();

        GridLayout grid = new GridLayout(7, 4, 0, 0);
        setLayout(grid);

        JLabel maurader = new JLabel("Marauder");
        maurader.setHorizontalAlignment(JLabel.CENTER);
        maurader.setFont(new Font("Fipps", Font.PLAIN, 20));
        maurader.setForeground(Color.BLACK);
        maurader.setBackground(new Color(0, 0, 0, 0));
        maurader.setToolTipText("Increases speed by 15% each rank.");


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

        JLabel blacksmith = new JLabel("Blacksmith");
        blacksmith.setHorizontalAlignment(JLabel.CENTER);
        blacksmith.setFont(new Font("Fipps", Font.PLAIN, 20));
        blacksmith.setForeground(Color.BLACK);
        blacksmith.setBackground(new Color(0, 0, 0, 0));
        blacksmith.setToolTipText("Increases dank memes by 15% each rank.");


        Queue<JLabel> labels = new ArrayBlockingQueue<>(4);

        labels.add(maurader);
        labels.add(warrior);
        labels.add(juggernaut);
        labels.add(blacksmith);

        while (!labels.isEmpty()){
            add(labels.poll());
        }


        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new GridLayout(1, 1));
        JLabel exitJLabel = new JLabel("Press Any Button To Go Back");
        exitJLabel.setHorizontalAlignment(JLabel.CENTER);
        exitJLabel.setFont(new Font("Fipps", Font.PLAIN, 20));
        exitJLabel.setForeground(Color.BLACK);
        exitPanel.add(exitJLabel);
        exitPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));


        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(1, 1));
        skillpoints = new JLabel();
        skillpoints.setText("Skill Points: " + player.getSkillPoints());
        skillpoints.setHorizontalAlignment(JLabel.CENTER);
        skillpoints.setFont(new Font("Fipps", Font.PLAIN, 30));
        skillpoints.setForeground(Color.BLACK);
        titlePanel.add(skillpoints);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        for (int i = 0; i < skillTree.getSkills().size(); i++) {
            Skill skill = skillTree.getSkillAt(i);
            skill.addActionListener(this);
            add(skill);
        }


        setFocusable(true);
        requestFocus();
        setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 60));
        setPreferredSize(new Dimension(GamePanel.WIDTH * (SCALE), GamePanel.HEIGHT * (SCALE) - 168));
        JPanel backgroundPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    bg.draw((Graphics2D) g);
                }
            }
        };
        window = new JFrame("Skill Tree");
        window.setLocation(Game.window.getX(), Game.window.getY() + 22);
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
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.pack();
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
        g = (Graphics2D) image.getGraphics();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //negatory

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
