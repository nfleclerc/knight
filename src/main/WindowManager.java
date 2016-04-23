/*
 * Copyright (c) 2016.
 */

package main;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Creates a system for managing additional windows
 */
public class WindowManager implements ComponentListener {

    public static JFrame subWindow;
    public static GamePanel subPanel;


    @Override
    public void componentResized(ComponentEvent e) {

    }

    /**
     * Detects if this window has been moved, and if it has, move the corresponding window
     * @param e The event of the component moving
     */
    @Override
    public void componentMoved(ComponentEvent e) {
        if (subWindow != null && subPanel != null) {
            subWindow.setLocation(Game.window.getX() +
                    subPanel.getXInset(), Game.window.getY() + subPanel.getYInset());
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

    /**
     * Sets a window as the subwindow. There can only be one subwindow at a time
     * @param subWindow a JFrame to set as the temporary subwindow
     */
    public static void setAsSubWindow(JFrame subWindow){
        WindowManager.subWindow = subWindow;
        subPanel = (GamePanel)subWindow.getContentPane();
    }

    /**
     * Removes the subwindow from this class
     */
    public static void removeSubWindow(){
        subPanel = null;
        subWindow = null;
    }
}
