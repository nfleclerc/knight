/*
 * Copyright (c) 2016.
 */

package save;

import entity.Player;
import skilltree.Skill;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;

/**
 * Created by nathaniel on 4/16/16.
 */
public class Saver {

    public Saver(Player player, SecretKey key){

        String dirPath = "AKnightOfCode/Saves/";

        new File(dirPath).mkdirs();

        String text = (player.getX() + "\n" +
        player.getY() + "\n"+
        player.getSkillPoints() + "\n"+
        player.getLevel() + "\n"+
        player.getAttackBonus() + "\n"+
        player.getDefenseBonus() + "\n"+
        player.getHealth() + "\n"+
        player.getMaxHealth() + "\n"+
        player.getFacingRight() + "\n"+
        player.getXP() + "\n"+
        player.getSkillTree().getSkills().toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace("\"", ""));



        try (FileOutputStream out = new FileOutputStream(dirPath + "knight.save")){

            Cipher desCipher = Cipher.getInstance("DES");

            byte[] byteText = text.getBytes("UTF8");

            desCipher.init(Cipher.ENCRYPT_MODE, key);

            out.write(desCipher.doFinal(byteText));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
