/*
 * Copyright (c) 2016.
 */

package save;

import audio.AudioPlayer;
import entity.Player;
import gameStates.GameStateManager;
import messages.Message;
import messages.MessageFactory;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.*;

/**
 * Loads the game from a save file
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

        //whether or not the background music should be stopped
        boolean shouldClose = true;

        File file = new File("AKnightOfCode/Saves/knight.save");

        try (FileInputStream in = new FileInputStream(file)) {

            //uses DES decryption
            Cipher desCipher = Cipher.getInstance("DES");
            desCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] textDecrypted = desCipher.doFinal(IOUtils.toByteArray(in));

            String[] attributes = new String(textDecrypted).split("\n");

            //retrieves all information located in the save file
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
                skillsActive[j] = Boolean.parseBoolean(activeFlags[j]);
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

        if (shouldClose) bgMusic.stop();

        //loads the world state with a player loaded from the attributes in the save file
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
