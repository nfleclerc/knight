/*
 * Copyright (c) 2016.
 */

package messages;

import main.GamePanel;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by nathaniel on 4/12/16.
 */
public class MessageFactory {

    private int targetTime;
    private Queue<Message> messagesToDisplay;

    private static MessageFactory ourInstance = new MessageFactory();

    public static MessageFactory getInstance() {
        return ourInstance;
    }

    private MessageFactory() {
        messagesToDisplay = new PriorityQueue<>();
        targetTime = 0;
    }

    public void draw(Graphics2D g){

        if (!messagesToDisplay.isEmpty()) {
            messagesToDisplay.peek().draw(g);
            if (messagesToDisplay.peek().getType() == Message.MessageType.DEATH){
                g.setFont(new Font("Arial", Font.BOLD, 14));
                String text = "Press R to Revert to Last Save or Q to Quit";
                g.drawString(text,
                        GamePanel.WIDTH / 2 - g.getFontMetrics().stringWidth(text) / 2,
                        140);
            }
        }

    }

    public void update(){
        if (!messagesToDisplay.isEmpty()) {
            targetTime++;
            if (targetTime == 120) {
                messagesToDisplay.poll();
                targetTime = 0;
            }
        }
    }

    public void createMessage(String string, Message.MessageType type){
        Message message = new Message(string, type);
        if (!messagesToDisplay.contains(message)) {
            messagesToDisplay.add(message);
        }
    }

    public void flush(){
        messagesToDisplay.clear();
    }

    public void flushOutput(){
        messagesToDisplay.removeIf(message -> message.getType() == Message.MessageType.COMPILE_ERROR
                || message.getType() == Message.MessageType.RUNTIME_ERROR);
    }

}
