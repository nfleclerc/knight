/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.levels.LevelState;
import main.Game;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by nathaniel on 3/10/16.
 *
 *
 */
public class AttackProcessor {

    private LevelState levelState;
    CodeWindow codeWindow;
    Class<?> compiledClass;

    public AttackProcessor(LevelState levelState){
        this.levelState = levelState;
        if (levelState.getPlayer().enemyInRange(levelState.getEnemies())){
            levelState.getGamePanel().setInterrupted(true);
            codeWindow = new CodeWindow(this);
        } else {
            levelState.getPlayer().setAttacking();
        }
    }

    public synchronized void processClick() {
        String filePath = makeFileFrom(codeWindow.getText());
        compile(filePath);
        run();
        if (codeWasSuccessFull()) {
            levelState.getGamePanel().setInterrupted(false);
            synchronized (Game.panel) {
                Game.panel.notify();
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
        String filePath = dirPath + getClassName(text);
        try(PrintWriter out = new PrintWriter(filePath + ".java")){
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
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> sourceFiles = fileManager.getJavaFileObjects(new File(filepath + ".java"));
        String classPath = "AKnightOfCode/Classes/";
        new File(classPath).mkdirs();
        final Iterable<String> options = Arrays.asList("-d", classPath);
        boolean successfulCompilation = compiler.getTask(null, fileManager, null, options, null, sourceFiles).call();

        try {
            if (successfulCompilation) {
                compiledClass = Thread.currentThread()
                        .getContextClassLoader().loadClass(filepath.replace('/', '.'));
            }
            System.out.print(compiledClass.getCanonicalName());
            fileManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return successfulCompilation;
    }

    public void run(){
        try {
            Process process = new ProcessBuilder("AKnightOfCode/Classes/").start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
