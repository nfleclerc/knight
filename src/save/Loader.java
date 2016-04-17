/*
 * Copyright (c) 2016.
 */

package save;

import audio.AudioPlayer;
import entity.Player;
import gameStates.GameStateManager;
import messages.Message;
import messages.MessageFactory;
import org.apache.commons.io.FileUtils;
import sun.misc.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;

/**
 * Created by nathaniel on 4/16/16.
 */
public class Loader {

    private  boolean[] skillsActive;
    private double x;
    private double y;
    private  int skillPoints;
    private  int level;
    private double attackBonus;
    private double defenseBonus;
    private  int health;
    private  int maxHealth;
    private  boolean facingRight;
    private  int XP;

    public Loader(GameStateManager gameStateManager, SecretKey key, AudioPlayer bgMusic){

        boolean shouldClose = true;

        File file = new File("AKnightOfCode/Saves/knight.save");

        try (FileInputStream in = new FileInputStream(file)) {

            Cipher desCipher = Cipher.getInstance("DES");

            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] textDecrypted = desCipher.doFinal(org.apache.commons.io.IOUtils.toByteArray(in));

            String[] attributes = new String(textDecrypted).split("\n");

            int i = 0;
            x = Double.parseDouble(attributes[i++]);
            y = Double.parseDouble(attributes[i++]);
            skillPoints = Integer.parseInt(attributes[i++]);
            level = Integer.parseInt(attributes[i++]);
            attackBonus = Double.parseDouble(attributes[i++]);
            defenseBonus = Double.parseDouble(attributes[i++]);
            health = Integer.parseInt(attributes[i++]);
            maxHealth = Integer.parseInt(attributes[i++]);
            facingRight = Boolean.parseBoolean(attributes[i++]);
            XP = Integer.parseInt(attributes[i++]);
            String[] activeFlags = attributes[i].split(" ");
            skillsActive = new boolean[activeFlags.length];
            for (int j = 0; j < skillsActive.length; j++) {
                skillsActive[i] = Boolean.parseBoolean(activeFlags[i]);
            }


        } catch (IOException e){
            MessageFactory.getInstance().createMessage("Couldn't Find Save File in AKnightOfCode/Saves",
                    Message.MessageType.FILEIO);
            shouldClose = false;
        } catch (Exception e){
            MessageFactory.getInstance().createMessage("Save File Is Corrupted",
                    Message.MessageType.FILEIO);
            shouldClose = false;
        }

        if (shouldClose) bgMusic.close();

        gameStateManager.loadWorldState(new Player(
                gameStateManager.getTileMap(),
                x,
                y,
                skillPoints,
                level,
                attackBonus,
                defenseBonus,
                health,
                maxHealth,
                facingRight,
                XP,
                skillsActive
        ));
    }

}
