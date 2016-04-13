/*
 * Copyright (c) 2016.
 */

package messages;

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
        }

    }

    public void update(){
        if (!messagesToDisplay.isEmpty()) {
            targetTime++;
            if (targetTime == 180) {
                messagesToDisplay.poll();
                targetTime = 0;
            }
        }
    }

    public void createMessage (String string, Message.MessageType type){
        Message message = new Message(string, type);
        if (!messagesToDisplay.contains(message)) {
            messagesToDisplay.add(message);
        }
    }

}
