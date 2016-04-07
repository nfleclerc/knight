/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import gameStates.GameStateManager;
import main.Game;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by nathaniel on 4/4/16.
 */
public class SkillDisplay extends GamePanel implements ActionListener {

    private final SkillTree skillTree;
    private final Player player;
    private JFrame window;

    //private JFrame window;
    private BufferedImage image;
    private Graphics2D g;
    private Label skillpoints;


    public SkillDisplay(Player player){
        GamePanel.interrupted = true;

        this.player = player;
        this.skillTree = player.getSkillTree();

        //window = new JFrame("Skills");
        GridLayout grid = new GridLayout(6, 4, 140, 20);
        setLayout(grid);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 4));

        Label maurader = new Label("Maurader");
        maurader.setAlignment(Label.LEFT);
        maurader.setFont(new Font("Courier New", Font.PLAIN, 30));

        Label warrior = new Label("Warrior");
        warrior.setAlignment(Label.CENTER);
        warrior.setFont(new Font("Courier New", Font.PLAIN, 30));

        Label juggernaut = new Label("Juggernaut");
        juggernaut.setAlignment(Label.CENTER);
        juggernaut.setFont(new Font("Courier New", Font.PLAIN, 30));

        Label blacksmith = new Label("Blacksmith");
        blacksmith.setAlignment(Label.RIGHT);
        blacksmith.setFont(new Font("Courier New", Font.PLAIN, 30));

        labelPanel.add(maurader);
        labelPanel.add(warrior);
        labelPanel.add(juggernaut);
        labelPanel.add(blacksmith);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2, 1));
        skillpoints = new Label();
        skillpoints.setText("Skill Points: " + player.getSkillPoints());
        skillpoints.setAlignment(Label.CENTER);
        skillpoints.setFont(new Font("Courier New", Font.PLAIN, 30));
        titlePanel.add(skillpoints);

        for (int i = 0; i < skillTree.getSkills().size(); i++) {
            Skill skill = skillTree.getSkillAt(i);
            skill.addActionListener(this);
            add(skill);
        }


        setFocusable(true);
        requestFocus();
        setPreferredSize(new Dimension(GamePanel.WIDTH * (SCALE), GamePanel.HEIGHT * (SCALE) - 80));
        window = new JFrame("Skill Tree");
        window.setLocation(Game.window.getX(), Game.window.getY());
        window.setUndecorated(true);
        window.setSize(GamePanel.WIDTH, GamePanel.HEIGHT);
        window.add(this, BorderLayout.SOUTH);
        window.add(titlePanel, BorderLayout.NORTH);
        window.add(labelPanel, BorderLayout.CENTER);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.buySkill(skillTree.getSkillAt(Integer.parseInt(e.getActionCommand())));
        skillTree.getSkillAt(Integer.parseInt(e.getActionCommand())).checkForRepaint(g);
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
        synchronized (Game.panel){
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
        g2.dispose();
    }
}
