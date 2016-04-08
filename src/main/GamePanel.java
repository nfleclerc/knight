package main;


import gameStates.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nathaniel on 2/18/16.
 *
 * Represents the panel displaying the game.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

    /**
     * The base width of the game window.
     */
    public static final int WIDTH = 320;

    /**
     * The base height of the game window.
     */
    public static final int HEIGHT = 240;

    /**
     * The scale at which to multiply the game window's height and width by.
     */
    public static final int SCALE = 3;

    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000 / FPS;

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gameStateManager;
    public static volatile boolean interrupted;

    /**
     * Creates a panel. Only to be used once, when the game is first being created.
     */
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        init();

        long start;
        long elapsed;
        long wait;

        while (running){

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1_000_000;
            if (wait < 0) { wait = 5;}

            try {
                Thread.sleep(wait);
                if (interrupted) {
                    synchronized (this) {
                        while (interrupted) {
                            wait();
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Draws the contents of the panel to the screen.
     */
    public void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }

    /**
     * Draws the current game state.
     */
    public void draw() {
        gameStateManager.draw(g);
    }

    /**
     * Updates the current game state.
     */
    public void update() {
        gameStateManager.update();
    }


    /**
     * Initializes the Graphics of the panel and creates a
     * game state manager.
     */
    public void init() {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        gameStateManager = new GameStateManager(this);


        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("/fonts/Fipps-Regular.otf")));
            for (Font font : ge.getAllFonts()){
                System.out.println(font);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameStateManager.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameStateManager.keyReleased(e.getKeyCode());
    }

    public synchronized void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }
}
