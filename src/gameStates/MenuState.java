package gameStates;

import tileMap.Background;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by nathaniel on 2/18/16.
 */
public class MenuState extends GameState {

    private Background bg;

    private int currentChoice = 0;

    private String[] options = {
            "Start",
            "Help",
            "Load",
            "Credits",
            "Quit"
    };

    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gameStateManager){
        this.gameStateManager = gameStateManager;
        try {
            bg = new Background("/backgrounds/mountainbg.gif", 1);
            bg.setVector(-0.1, 0);
            titleColor = Color.RED;
            titleFont = new Font("Arial", Font.PLAIN, 28);
            font = new Font("Arial", Font.PLAIN, 12);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        bg.update();
    }

    @Override
    public void draw(Graphics2D g) {

        bg.draw(g);

        //draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("A Knight of Code", 50, 70);

        //draw menu
        g.setFont(font);
        for (int i = 0; i < options.length; i++) {
            if (i == currentChoice){
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
    }

    @Override
    public void keyPressed(int k) {
        switch (k){
            case KeyEvent.VK_ENTER:
                select();
                break;
            case KeyEvent.VK_UP:
                currentChoice--;
                if (currentChoice == -1){
                    currentChoice = options.length - 1;
                }
                break;
            case KeyEvent.VK_DOWN:
                currentChoice++;
                if (currentChoice == options.length){
                    currentChoice = 0;
                }
                break;
        }
    }

    private void select(){
        switch (currentChoice){
            case 0:
                //start
                gameStateManager.setState(GameStateManager.FORESTSTATE);
                break;
            case 1:
                //help
                break;
            case 2:
                //load game
                break;
            case 3:
                //quit
                System.exit(0);
                break;
            default:
                System.exit(0);
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
