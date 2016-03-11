/*
 * Copyright (c) 2016.
 */

package evaluator;

import main.GamePanel;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 3/9/16.
 *
 * Creates a new window to capture input for the code parser.
 */
public class CodeWindow extends GamePanel implements ActionListener{

    private BufferedImage image;
    private Graphics2D g;
    private boolean running;
    private JTextPane editor;
    private JButton button;
    private JFrame window;
    private static final int WIDTH = GamePanel.WIDTH * (SCALE - 1);
    private static final int HEIGHT = GamePanel.HEIGHT * (SCALE - 1);
    private AttackProcessor attackProcessor;

    public CodeWindow(AttackProcessor attackProcessor){
        this.attackProcessor = attackProcessor;
        StyleContext styleContext = new StyleContext();
        Style style = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        Style constantWidthStyle = styleContext.addStyle("ConstantWidth", null);

        StyleConstants.setForeground(constantWidthStyle, new Color(176, 0, 72));
        StyleConstants.setBold(constantWidthStyle, true);

        editor = new JTextPane(new Highlighter(style, constantWidthStyle));
        editor.setFont(new Font("Courier New", Font.PLAIN, 12));
        editor.setOpaque(false);

        JScrollPane scrollingEditor = new JScrollPane(editor);
        scrollingEditor.setPreferredSize(new Dimension(WIDTH - 20, HEIGHT - 80));
        add(scrollingEditor, BorderLayout.CENTER);

        button = new JButton("Attack!");
        button.addActionListener(this);
        JPanel buttonCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonCenter.add(button);
        add(buttonCenter, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        window = new JFrame("Write Code to Attack!");
        window.setContentPane(this);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void init() {
        image = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
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

    @Override
    public void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

    public String getText(){
        return editor.getText();
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        attackProcessor.processClick();
        window.dispose();
    }
}
