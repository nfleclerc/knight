/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by nathaniel on 4/4/16.
 */
public class SkillDisplay extends GamePanel implements ActionListener {

    private final SkillTree skillTree;
    private final Player player;
    private ArrayList<JButton> mauraderButtons;
    private ArrayList<JButton> juggernautButtons;
    private ArrayList<JButton> warriorButtons;
    private ArrayList<JButton> blacksmithButtons;

    private JFrame window;


    public SkillDisplay(Player player){
        this.player = player;
        this.skillTree = player.getSkillTree();


        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        this.setOpaque(false);
        window = new JFrame("Write Code to Attack!");
        window.setBackground(new Color(0, 0, 0, 0));
        window.setUndecorated(true);
        window.setContentPane(this);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
