/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.levels.LevelState;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
        String filePath = makeFileFrom(codeWindow.getText());
        compile(filePath);
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
        String filePath = dirPath + getClassName(text) + ".java";
        try(PrintWriter out = new PrintWriter(filePath)){
            out.print(text);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return filePath;
    }

    private String getClassName(String text) {
        String[] words = text.split("\\s");
        int i = 0;
        while (i < words.length){
            if (!isKeyword(words[i])){
                break;
            }
            i++;
        }
        return words[i];
    }

    private static boolean isKeyword(String word) {
        return(word.trim().equals("private") ||
                word.trim().equals("public") ||
                word.trim().equals("protected") ||
                word.trim().equals("class") ||
                word.trim().equals("static") ||
                word.trim().equals("return") ||
                word.trim().equals("final") ||
                word.trim().equals("abstract") ||
                word.trim().equals("enum") ||
                word.trim().equals("interface") ||
                word.trim().equals("strictfp"));
    }

    private boolean compile(String filepath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compilationResult = compiler.run(null, null, null, filepath);
        return compilationResult == 0;
    }


}
