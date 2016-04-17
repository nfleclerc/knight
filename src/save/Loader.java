/*
 * Copyright (c) 2016.
 */

package save;

import entity.Player;
import gameStates.GameStateManager;
import java.io.BufferedReader;
import java.io.FileReader;

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
    private  int XP ;

    public Loader(GameStateManager gameStateManager){

        try (BufferedReader br = new BufferedReader(new FileReader("AKnightOfCode/Saves/knight.save"))) {
            x = Double.parseDouble(br.readLine());
            y = Double.parseDouble(br.readLine());
            skillPoints = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            attackBonus = Double.parseDouble(br.readLine());
            defenseBonus = Double.parseDouble(br.readLine());
            health = Integer.parseInt(br.readLine());
            maxHealth = Integer.parseInt(br.readLine());
            facingRight = Boolean.parseBoolean(br.readLine());
            XP = Integer.parseInt(br.readLine());
            String[] strings = br.readLine().split(" ");
            skillsActive = new boolean[strings.length];
            for (int i = 0; i < skillsActive.length; i++) {
                skillsActive[i] = Boolean.parseBoolean(strings[i]);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

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
