/*
 * Copyright (c) 2016.
 */

package messages;

import main.GamePanel;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A Singleton class that can be called from virtually anywhere to display messages to the screen.
 * */
public class MessageFactory {

    private int targetTime;
    private Queue<Message> messagesToDisplay;

    private static MessageFactory ourInstance = new MessageFactory();

    /**
     * Gets the single instance of a MessageFactory.
     * @return the singleton instance of a MessageFactory
     */
    public static MessageFactory getInstance() {
        return ourInstance;
    }

    private MessageFactory() {
        messagesToDisplay = new PriorityQueue<>();
        targetTime = 0;
    }

    /**
     * Draws a message to the screen
     * @param g the graphics of the screen
     */
    public void draw(Graphics2D g){
        if (!messagesToDisplay.isEmpty()) {
            messagesToDisplay.peek().draw(g);
            //if the message type is dead, add some subtext instructing the player on what to do
            if (messagesToDisplay.peek().getType() == Message.MessageType.DEATH){
                g.setFont(new Font("Arial", Font.BOLD, 14));
                String text = "Press R to Revert to Last Save or Q to Quit";
                g.drawString(text,
                        GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(text) / 2,
                        140);
            }
        }

    }

    /**
     * Decides when a message has been displayed enough and when it should be removed. Messages
     * normally last for three seconds
     */
    public void update(){
        if (!messagesToDisplay.isEmpty()) {
            targetTime++;
            if (targetTime == 180) {
                messagesToDisplay.poll();
                targetTime = 0;
            }
        }
    }

    /**
     * Creates a new message to be displayed
     * @param string The actual message
     * @param type The type of the message
     */
    public void createMessage(String string, Message.MessageType type){
        Message message = new Message(string, type);
        if (!messagesToDisplay.contains(message)) {
            messagesToDisplay.add(message);
        }
    }

    /**
     * Removes any messages that are still waiting to appear
     */
    public void flush(){
        messagesToDisplay.clear();
    }

    /**
     * Removes any output error messages that are still waiting to appear
     */
    public void flushOutput(){
        messagesToDisplay.removeIf(message -> message.getType() == Message.MessageType.COMPILE_ERROR
                || message.getType() == Message.MessageType.RUNTIME_ERROR);
    }

}
