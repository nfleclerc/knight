/*
 * Copyright (c) 2016.
 */

package save;

import entity.Player;
import skilltree.Skill;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nathaniel on 4/16/16.
 */
public class Saver {

    public Saver(Player player){

        String dirPath = "AKnightOfCode/Saves/";

        new File(dirPath).mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dirPath + "knight.save"))){
            bw.write(player.getX() + "\n");
            bw.write(player.getY() + "\n");
            bw.write(player.getSkillPoints() + "\n");
            bw.write(player.getLevel() + "\n");
            bw.write(player.getAttackBonus() + "\n");
            bw.write(player.getDefenseBonus() + "\n");
            bw.write(player.getHealth() + "\n");
            bw.write(player.getMaxHealth() + "\n");
            bw.write(player.getFacingRight() + "\n");
            bw.write(player.getXP() + "\n");
            bw.write(player.getSkillTree().getSkills().toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", "")
                    .replace("\"", ""));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
