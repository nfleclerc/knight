/*
 * Copyright (c) 2016.
 */

package messages;

import main.GamePanel;

import java.awt.*;

/**
 * Created by nathaniel on 4/12/16.
 */
public class Message implements Comparable<Message>{

    private final String message;
    private final int priority;
    private Color color;
    private Font font;

    public enum MessageType {

        RUNTIME_ERROR(new Font("Arial", Font.PLAIN, 12), Color.PINK, 2, "Runtime Error: "),
        COMPILE_ERROR(new Font("Arial", Font.PLAIN, 12), Color.RED, 1, "Compile Error: "),
        LEVEL_UP(new Font("Arial", Font.BOLD, 40), new Color(186, 168, 36, 255), 4, ""),
        TIP(new Font("Arial", Font.ITALIC, 12), Color.YELLOW, 5, ""),
        WARNING(new Font("Arial", Font.PLAIN, 12), Color.MAGENTA, 3, "");

        private Color color;
        private Font font;
        private int priority;
        private String prefix;

        MessageType(Font font, Color color, int priority, String prefix) {
            this.font = font;
            this.color = color;
            this.priority = priority;
            this.prefix = prefix;
        }

        int getPriority(){
            return priority;
        }

        Color getColor() {
             return color;
        }

        Font getFont() {
            return font;
        }

        String getPrefix(){
            return prefix;
        }
    }

    protected Message(String message, MessageType type) {

            this.message = type.getPrefix() + message;
            priority = type.getPriority();
            color = type.getColor();
            font = type.getFont();

    }

    protected void draw(Graphics2D g){
        g.setFont(font);
        g.setColor(color);
        int width = g.getFontMetrics().stringWidth(message);
        g.drawString(message, GamePanel.WIDTH / 2 - width / 2, 70);
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Message message) {
        return Integer.compare(this.getPriority(), message.getPriority());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Message && this.message.equals(((Message) obj).message);
    }
}
