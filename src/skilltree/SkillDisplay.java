/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import gameStates.GameStateManager;
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
    private BufferedImage image;
    private Graphics2D g;


    public SkillDisplay(Player player){
        this.player = player;
        this.skillTree = player.getSkillTree();

        window = new JFrame("Skills");
        GridLayout grid = new GridLayout(5, 4, 80, 30);
        setLayout(grid);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 4));

        Label maurader = new Label("Maurader");
        maurader.setAlignment(Label.CENTER);
        maurader.setFont(new Font("Arial", Font.PLAIN, 30));

        Label warrior = new Label("Warrior");
        warrior.setAlignment(Label.CENTER);
        warrior.setFont(new Font("Arial", Font.PLAIN, 30));

        Label juggernaut = new Label("Juggernaut");
        juggernaut.setAlignment(Label.CENTER);
        juggernaut.setFont(new Font("Arial", Font.PLAIN, 30));

        Label blacksmith = new Label("Blacksmith");
        blacksmith.setAlignment(Label.CENTER);
        blacksmith.setFont(new Font("Arial", Font.PLAIN, 30));

        labelPanel.add(maurader);
        labelPanel.add(warrior);
        labelPanel.add(juggernaut);
        labelPanel.add(blacksmith);


        for (int i = 0; i < skillTree.getSkills().size(); i++) {
            Skill skill = skillTree.getSkillAt(i);
            skill.addActionListener(this);
            add(skill);
        }

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        window.setUndecorated(false);
        window.add(this, BorderLayout.SOUTH);
        window.add(labelPanel, BorderLayout.NORTH);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.buySkill(skillTree.getSkillAt(Integer.parseInt(e.getActionCommand())));
        skillTree.getSkillAt(Integer.parseInt(e.getActionCommand())).checkForRepaint(g);
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
