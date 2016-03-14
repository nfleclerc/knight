/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.levels.LevelState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by nathaniel on 3/10/16.
 *
 *
 */
public class AttackProcessor {

    private LevelState levelState;
    CodeWindow codeWindow;

    public AttackProcessor(LevelState levelState){
        this.levelState = levelState;
        if (levelState.getPlayer().enemyInRange(levelState.getEnemies())){
            levelState.getGamePanel().setProcessingAttack(true);
            codeWindow = new CodeWindow(this);
        } else {
            levelState.getPlayer().setAttacking();
        }
    }

    public synchronized void processClick() {
        makeFileFrom(codeWindow.getText());
        if (codeWasSuccessFull()) {
            levelState.getGamePanel().setProcessingAttack(false);
            synchronized (levelState.getGamePanel()) {
                levelState.getGamePanel().notify();
            }
            levelState.getPlayer().setAttacking();
        }
    }

    private boolean codeWasSuccessFull() {
        return true;
    }

    public LevelState getLevelState() {
        return levelState;
    }

    private String makeFileFrom(String text){
        String dirPath = "AKnightOfCode/Programs/";
        new File(dirPath).mkdirs();
        String randomHash = new BigInteger(130, new Random()).toString(32);
        String filePath = dirPath + randomHash +".java";
        try(PrintWriter out = new PrintWriter(filePath)){
            out.print(text);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return filePath;
    }
}
