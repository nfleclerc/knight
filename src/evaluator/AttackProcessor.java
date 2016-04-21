/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.LevelState;
import gameStates.WorldState;
import main.Game;
import messages.Message;
import messages.MessageFactory;

import javax.tools.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nathaniel on 3/10/16.
 *
 *
 */
public class AttackProcessor {

    private LevelState levelState;
    CodeWindow codeWindow;
    Class<?> compiledClass;
    private String className;
    private boolean successfulRun;
    private boolean successfulCompilation;
    private String prompt;

    public AttackProcessor(LevelState levelState){
        this.levelState = levelState;
        prompt = LevelState.testBank.getTest(levelState.getPlayer());
        if (levelState.getPlayer().enemyInRange(levelState.getEnemies())){
            levelState.getGamePanel().setInterrupted(true);
            codeWindow = new CodeWindow(prompt, this);
        } else {
            levelState.getPlayer().setAttacking();
        }
    }

    public synchronized void processClick() {
        String filePath = makeFileFrom(codeWindow.getText());
        compile(filePath);
        if (successfulCompilation) {
            run();
        }
        levelState.getGamePanel().setInterrupted(false);
        synchronized (Game.panel) {
            Game.panel.notify();
        }
        if (codeWasSuccessFull()) {
            levelState.getPlayer().setAttacking();
        }
    }

    private boolean codeWasSuccessFull() {
        return successfulCompilation && successfulRun;
    }

    public LevelState getLevelState() {
        return levelState;
    }

    private String makeFileFrom(String text){
        String dirPath = "AKnightOfCode/Programs/";
        new File(dirPath).mkdirs();
        className = getClassName(text);
        String filePath = dirPath + className;
        try(PrintWriter out = new PrintWriter(filePath + ".java")){
            out.print(text);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return filePath;
    }

    private String getClassName(String text) {
        String[] lines = text.split("\n");
        String classDeclarationLine = "";
        for (String line : lines){
            if (line.contains("class")) {
                classDeclarationLine = line;
            }
        }
        String[] words = classDeclarationLine.split("\\s");
        int i = 0;
        while (i < words.length){
            if (!isKeyword(words[i])){
                break;
            }
            i++;
        }
        return words[i].replace("{", "").replace("}", "");
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

    private void compile(String filepath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null){
            MessageFactory.getInstance().createMessage(
                    "Compiler Not Found. Please Download The Latest JDK.",
                    Message.MessageType.COMPILE_ERROR);
            return;
        }
        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        Iterable<? extends JavaFileObject> sourceFiles = fileManager.getJavaFileObjects(new File(filepath + ".java"));
        String classPath = "AKnightOfCode/Classes/";
        new File(classPath).mkdirs();
        final Iterable<String> options = Arrays.asList("-d", classPath);
        successfulCompilation = compiler.getTask(null, fileManager,
                diagnosticsCollector, options, null, sourceFiles).call();
        try {
            if (!successfulCompilation) {
                List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
                    MessageFactory.getInstance().createMessage(diagnostic.getMessage(null),
                            Message.MessageType.COMPILE_ERROR);
                }
            } else {
                ClassLoader cl = new URLClassLoader(
                        new URL[]{new File(classPath).toURI().toURL()});
                compiledClass = cl.loadClass((className));
                System.out.println(compiledClass);
            }
            fileManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void run(){
        try {
            Method m = compiledClass.getMethod("attack", String[].class);
            String result =
                    String.valueOf(m.invoke(compiledClass.newInstance(), (Object) new String[]{}));
            String answer = LevelState.testBank.getAnswer(prompt);
            if (levelState.getPlayer().getXP() > 0) {
                if (answer.trim().toLowerCase()
                        .equals(result.trim().toLowerCase())) {
                    successfulRun = true;
                } else {
                    successfulRun = false;
                    MessageFactory.getInstance().createMessage("Correct Answer: " + answer,
                            Message.MessageType.RUNTIME_ERROR
                    );
                }
            } else {
                successfulRun = true;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
