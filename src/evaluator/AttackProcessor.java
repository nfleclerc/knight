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
 * Process an attack
 */
public class AttackProcessor {

    private LevelState levelState;
    CodeWindow codeWindow;
    Class<?> compiledClass;
    private String className;
    private boolean successfulRun;
    private boolean successfulCompilation;
    private String prompt;

    /**
     * Creates a new code window if the player is in range of an enemy, otherwise
     * it just lets the player swing their sword
     * @param levelState the levelstate this is occuring in
     */
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

    /**
     * Saves the file, compiles it, and then runs it
     * If successful, the player attacks
     */
    public synchronized void processClick() {
        String filePath = makeFileFrom(codeWindow.getText());
        compile(filePath);
        if (successfulCompilation) {
            run();
        }
        //notifies the main thread and continues the game
        levelState.getGamePanel().setInterrupted(false);
        synchronized (Game.panel) {
            Game.panel.notify();
        }
        if (codeWasSuccessFull()) {
            levelState.getPlayer().setAttacking();
        }
    }

    /**
     * Returns true if the code both compiled and ran
     */
    private boolean codeWasSuccessFull() {
        return successfulCompilation && successfulRun;
    }

    public LevelState getLevelState() {
        return levelState;
    }

    /**
     * Makes a file from the given text
     * @param text the text to make a file from
     * @return the filePath that the file is located at
     */
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

    /**
     * Parses a textfile to get the ClassName
     * @param text the text to search through
     * @return the name of the class that the file represents
     */
    private String getClassName(String text) {
        String[] lines = text.split("\n");
        String classDeclarationLine = "";
        //find the line that declares the class
        for (String line : lines){
            if (line.contains("class")) {
                classDeclarationLine = line;
            }
        }
        //split this line and get the name
        String[] words = classDeclarationLine.split("\\s");
        int i = 0;
        while (i < words.length){
            if (!isKeyword(words[i])){
                break;
            }
            i++;
        }
        //return the name and trim off any excess chars
        return words[i].replace("{", "").replace("}", "");
    }

    /**
     * Returns true if a word is a keyword that can
     * appear in class titles or not
     * @param word the word to check
     */
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

    /**
     * Compiles a file at a given filepath. This method requires a JDK to be
     * installed on the machine it is run on
     * @param filepath the filepath the file is located at
     */
    private void compile(String filepath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //make sure a compiler is present
        if (compiler == null){
            MessageFactory.getInstance().createMessage(
                    "Compiler Not Found. Please Download The Latest JDK.",
                    Message.MessageType.COMPILE_ERROR);
            return;
        }
        //the diagnostic collector allows output to be shown to the player
        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null);
        //represents all the sourcefiles at a filepath
        Iterable<? extends JavaFileObject> sourceFiles = fileManager.getJavaFileObjects(new File(filepath + ".java"));
        String classPath = "AKnightOfCode/Classes/";
        //make the folder for the compiler output
        new File(classPath).mkdirs();
        //pass in options to the compiler
        final Iterable<String> options = Arrays.asList("-d", classPath);
        //compile the sourcefiles
        successfulCompilation = compiler.getTask(null, fileManager,
                diagnosticsCollector, options, null, sourceFiles).call();
        try {
            if (!successfulCompilation) {
                //if the sources did not compile, get the diagnostics and
                //display them to the screen as a message
                List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
                    MessageFactory.getInstance().createMessage(diagnostic.getMessage(null),
                            Message.MessageType.COMPILE_ERROR);
                }
            } else {
                //the sources compiled, load the class
                ClassLoader cl = new URLClassLoader(
                        new URL[]{new File(classPath).toURI().toURL()});
                compiledClass = cl.loadClass((className));
            }
            fileManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Runs the loaded class file. Is only called once a file has been compiled and the
     * resulting class is loaded
     */
    public void run(){
        try {
            //get the method that the user filled in
            Method m = compiledClass.getMethod("attack", String[].class);
            //get the result of the method and parse it to a string
            String result =
                    String.valueOf(m.invoke(compiledClass.newInstance(), (Object) new String[]{}));
            //get the answer for the prompt
            String answer = LevelState.testBank.getAnswer(prompt);
            if (levelState.getPlayer().getXP() > 0) {
                //if the player has gotten in an engagement before compare the answer to the
                //to the result
                if (answer.trim().toLowerCase().equals(result.trim().toLowerCase())) {
                    //the answer matches the result
                    successfulRun = true;
                } else {
                    //the result of the inputted code was incorrect, output the correct
                    //answer to the screen as a message
                    successfulRun = false;
                    MessageFactory.getInstance().createMessage("Correct Answer: " + answer,
                            Message.MessageType.RUNTIME_ERROR
                    );
                }
            } else {
                //the player has not been in an entanglement before, and thus the code
                //should automatically pass
                successfulRun = true;
            }
        } catch (InvocationTargetException |
                NoSuchMethodException |
                IllegalAccessException |
                InstantiationException e) {
            e.printStackTrace();
        }
    }

}
