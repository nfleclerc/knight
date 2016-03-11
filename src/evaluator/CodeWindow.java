/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.GameStateManager;
import main.GamePanel;
import tileMap.Background;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
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
    private int FPS = 60;
    private long targetTime = 1000 / FPS;
    private JTextPane editor;
    private JButton button;
    private JFrame window;
    private Background bg;
    private Background textbg;
    private Background buttonbg;
    private static final int SCALE = GamePanel.SCALE - 1;
    private AttackProcessor attackProcessor;

    public CodeWindow(AttackProcessor attackProcessor){
        this.attackProcessor = attackProcessor;

        StyleContext styleContext = new StyleContext();
        Style style = styleContext.getStyle(StyleContext.DEFAULT_STYLE);
        Style constantWidthStyle = styleContext.addStyle("ConstantWidth", null);

        StyleConstants.setForeground(constantWidthStyle, new Color(176, 0, 72));
        StyleConstants.setBold(constantWidthStyle, true);

        editor = new JTextPane(new Highlighter(style, constantWidthStyle)) {
            @Override
            protected void paintComponent(Graphics g) {
                textbg.draw((Graphics2D) g);
                super.paintComponent(g);
            }
        };
        editor.setFont(new Font("Courier New", Font.PLAIN, 15));
        editor.setOpaque(false);

        JScrollPane scrollingEditor = new JScrollPane(editor);
        scrollingEditor.setPreferredSize(new Dimension(WIDTH * SCALE - 20, HEIGHT * SCALE - 80));
        scrollingEditor.setOpaque(false);
        add(scrollingEditor, BorderLayout.CENTER);

        buttonbg = new Background("/backgrounds/buttonbg.gif", 1);
        button = new JButton("Attack!") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                buttonbg.draw((Graphics2D)g);
            }
        };

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBorder(null);
            }
        });


        //button.setBorder(null);
        //button.setBorderPainted(false);
        //button.setMargin(new Insets(0, 0, 0, 0));
        button.setPreferredSize(new Dimension(WIDTH * SCALE - 30, 50));
        button.addActionListener(this);
        JPanel buttonCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonCenter.add(button);
        add(buttonCenter, BorderLayout.SOUTH);
        button.setOpaque(false);

        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
        window = new JFrame("Write Code to Attack!");
        window.setUndecorated(true);
        window.setLocation(GamePanel.WIDTH * GamePanel.SCALE - WIDTH * SCALE - 50, GamePanel.HEIGHT - 150);
        window.setContentPane(this);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    @Override
    public void init() {
        image = new BufferedImage(GamePanel.WIDTH, GamePanel.HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        setBackgroundByLevel();
        running = true;
    }

    private void setBackgroundByLevel(){
        switch (attackProcessor.getLevelState()
                .getGameStateManager().getCurrentState()){
            case GameStateManager.FORESTSTATE:
                bg = new Background("/backgrounds/forestbg_attackwindow.gif", 1);
                textbg = new Background("/backgrounds/forest_textwindow.gif", 1);
                break;
            case GameStateManager.MOUNTAINSTATE:
                bg = new Background("/backgrounds/mountainbg_attackwindow.gif", 1);
                textbg = new Background("/backgrounds/mountain_textwindow.gif", 1);
                break;
            default:

        }
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
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.draw((Graphics2D)g);
    }
}
