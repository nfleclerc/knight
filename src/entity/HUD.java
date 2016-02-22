package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nathaniel on 2/21/16.
 */
public class HUD {

    private Player player;

    private BufferedImage image;
    private Font font;

    public HUD (Player player) {
        this.player = player;

        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/HUD/hud.gif"
                    )
            );
            font = new Font("Arial", Font.PLAIN, 14);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g){
        g.drawImage(image, 0, 10, null);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25);
        g.drawString(String.valueOf(player.getXP()), 30, 45);
    }

}
