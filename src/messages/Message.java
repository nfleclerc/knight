/*
 * Copyright (c) 2016.
 */

package messages;

import main.GamePanel;

import java.awt.*;

/**
 * A message that is displayed to the screen
 * */
public class Message implements Comparable<Message>{

    private final String message;
    private final int priority;
    private int height;
    private Color color;
    private Font font;
    private MessageType type;

    //the different kinds of messaages that can appear on the screen
    public enum MessageType {

        RUNTIME_ERROR(new Font("Arial", Font.PLAIN, 12), Color.PINK, 2, "Runtime Error: ", 70),
        COMPILE_ERROR(new Font("Arial", Font.PLAIN, 12), Color.RED, 1, "Compile Error: ", 70),
        LEVEL_UP(new Font("Arial", Font.BOLD, 40), new Color(186, 168, 36, 255), 4, "", 80),
        TIP(new Font("Arial", Font.ITALIC, 12), Color.YELLOW, 5, "", 70),
        WARNING(new Font("Arial", Font.PLAIN, 12), Color.MAGENTA, 3, "", 70),
        FILEIO(new Font("Arial", Font.PLAIN, 12), Color.RED, 6, "", GamePanel.HEIGHT - 20),
        DEATH(new Font("Arial", Font.BOLD, 60), new Color(110, 0, 7, 255), 0, "", GamePanel.HEIGHT / 2);

        private Color color;
        private Font font;
        private int priority;
        private String prefix;
        private int height;

        MessageType(Font font, Color color, int priority, String prefix, int height) {
            this.font = font;
            this.color = color;
            this.priority = priority;
            this.prefix = prefix;
            this.height = height;
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

        public int getHeight() {
            return height;
        }
    }

    /**
     * Creates a new message. Only for use in the MessageFactory class
     * @param message The actual message to be displayed on the screen. Must be able
     *                to fit on the screen in one line.
     * @param type  The type of message
     */
    protected Message(String message, MessageType type) {

        this.message = type.getPrefix() + message;
        this.type = type;
        priority = type.getPriority();
        color = type.getColor();
        font = type.getFont();
        height = type.getHeight();
    }

    /**
     * Draws this message to the screen
     * @param g the graphics of the screen
     */
    protected void draw(Graphics2D g){
        g.setFont(font);
        g.setColor(color);
        int width = g.getFontMetrics().stringWidth(message);
        g.drawString(message, GamePanel.WIDTH / 2 - width / 2, height);
    }

    public MessageType getType() {
        return type;
    }

    /**
     * Compares two messages. Message priority is based on its type
     * @param message the message to compare this message to
     */
    @Override
    public int compareTo(Message message) {
        return Integer.compare(this.priority, message.priority);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Message && this.message.equals(((Message) obj).message);
    }
}
