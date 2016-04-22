/*
 * Copyright (c) 2016.
 */

package main;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by nathaniel on 4/14/16.
 */
public class WindowManager implements ComponentListener {

    public static JFrame subWindow;
    public static GamePanel subPanel;


    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        if (subWindow != null && subPanel != null) {
            subWindow.setLocation(Game.window.getX() + subPanel.getXInset(), Game.window.getY() + subPanel.getYInset());
            subPanel.requestFocus();
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        subWindow.setVisible(false);
    }

    public static void setAsSubWindow(JFrame subWindow){
        WindowManager.subWindow = subWindow;
        subPanel = (GamePanel)subWindow.getContentPane();
    }

    public static void removeSubWindow(){
        subPanel = null;
        subWindow = null;
    }
}
