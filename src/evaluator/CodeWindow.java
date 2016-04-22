/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.LevelState;
import main.Game;
import main.GamePanel;
import main.WindowManager;
import tileMap.Background;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by nathaniel on 3/9/16.
 *
 * Creates a new window to capture input for the code parser.
 */
public class CodeWindow extends GamePanel implements ActionListener{

    private JTextPane editor;
    private JButton button;
    private JFrame window;
    private Background unclickedbg;
    private Background clickedbg;
    private static final int SCALE = GamePanel.SCALE - 1;
    private AttackProcessor attackProcessor;
    private boolean unclicked;

    public CodeWindow(String prompt, AttackProcessor attackProcessor){
        this.attackProcessor = attackProcessor;
        this.unclicked = true;

        StyleContext styleContext = new StyleContext();
        Style style = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        Style constantWidthStyle = styleContext.addStyle("ConstantWidth", null);

        StyleConstants.setForeground(constantWidthStyle, new Color(176, 0, 72));
        StyleConstants.setBold(constantWidthStyle, true);

        editor = new JTextPane(new Highlighter(style, constantWidthStyle));
        editor.setFont(new Font("Courier New", Font.PLAIN, 15));
        editor.setOpaque(false);

        if (attackProcessor.getLevelState().getPlayer().getXP() > 0) {
            editor.setText(prompt + LevelState.testBank.getCode());
        } else {
            editor.setText(prompt);
        }

        JScrollPane scrollingEditor = new JScrollPane(editor);
        scrollingEditor.setPreferredSize(new Dimension(WIDTH * SCALE - 20, HEIGHT * SCALE - 80));
        scrollingEditor.setOpaque(false);
        add(scrollingEditor, BorderLayout.CENTER);

        clickedbg = new Background("/background/button_clicked.gif", 1);
        unclickedbg = new Background("/background/button_unclicked.gif", 1);
        button = new JButton("Attack!") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                if (unclicked) {
                    unclickedbg.draw(g2);
                } else {
                    clickedbg.draw(g2);
                }
            }
        };

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                unclicked = false;
                button.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                unclicked = true;
                button.repaint();
            }
        });



        button.setPreferredSize(new Dimension(WIDTH * SCALE - 30, 50));
        button.setOpaque(false);
        button.addActionListener(this);
        JPanel buttonCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0, 0, 0, 0));
                g2.drawRect(0, 0, this.getWidth(), this.getHeight());
                SwingUtilities.getWindowAncestor(this).repaint();
            }
        };
        buttonCenter.add(button);
        add(buttonCenter, BorderLayout.SOUTH);


        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        setOpaque(false);
        window = new JFrame("Write Code to Attack!");
        window.setUndecorated(true);
        window.setLocation(Game.window.getX() + getXInset(), Game.window.getY() + getYInset() + 20);
        window.setContentPane(this);
        window.setBackground(new Color(0, 0, 0, 0));
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        WindowManager.setAsSubWindow(window);
        window.setVisible(true);
    }

    @Override
    public int getXInset(){
        return 250;
    }

    @Override
    public int getYInset(){
        return 50;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //negatory
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //nada
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //more nada
    }

    public String getText(){
        return editor.getText();
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        attackProcessor.processClick();
        WindowManager.removeSubWindow();
        window.dispose();
    }

}
