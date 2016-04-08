/*
 * Copyright (c) 2016.
 */

package crafting;

import entity.Player;
import tileMap.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by nathaniel on 4/8/16.
 */
public class CraftButton extends JRadioButton {

    private Background selectedBg;
    private Background unselectedBg;
    private Background lockedBg;
    private boolean locked;

    public CraftButton(Player player, String selectedBg, String unselectedBg, String lockedBg){

        this.selectedBg = new Background(selectedBg, 1);
        this.unselectedBg = new Background(unselectedBg, 1);
        this.lockedBg = new Background(lockedBg, 1);

        //setSelectedIcon(new ImageIcon(selectedBg));
        //setIcon(new ImageIcon(unselectedBg));

        //repaint();

    }


}
