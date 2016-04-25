/*
 * Copyright (c) 2016.
 */

package evaluator;

import entity.Player;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Holds all of the prompts used in the game
 * */
public class TestBank {

    private final HashMap<String, String> testAnswerMap;
    private String intro;
    private Random random = new Random(12345);

    public TestBank(){
        this.testAnswerMap = new HashMap<>();

        //parse the CSV file and put the results into a  a hashmap
        try (BufferedReader br = new BufferedReader(new FileReader("res/tests/tests.csv"))){
            String line;
            while ((line = br.readLine()) != null){
                String[] tokens = line.split(",");
                testAnswerMap.put(tokens[0], tokens[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            intro = IOUtils.toString(new InputStreamReader(new FileInputStream("res/tests/intro.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * If the player has yet to defeat an enemy, display the intro text.
     * Otherwise display a prompt at random
     * @param player The player attacking
     * @return the prompt to display in the codewindow
     */
    public String getTest(Player player){
        return player.getXP() == 0 ? intro : getRandomTest();
    }

    /**
     * The code that is displayed in the codewindow
     * @return A String representing the code that is displayed in the main window
     */
    public String getCode() {
        return "\n\npublic class /* Write Class Name Here */ { \n" +
                "\n" +
                "\t public String attack(String[] args) {\n" +
                "\t\t/* Your Code Here */\n" +
                "\t}\n" +
                "}";
    }

    /**
     * Gets a prompt at random from the map
     * @return a prompt
     */
    private String getRandomTest() {
        List<String> keys = new ArrayList<>(testAnswerMap.keySet());
       return  keys.get(random.nextInt(testAnswerMap.size()));
    }

    /**
     * Returns the answer for any given prompt
     * @param prompt the prompt for which you need an answer
     * @return the answer
     */
    public String getAnswer(String prompt){
        System.out.println((testAnswerMap.get(prompt)));
        return testAnswerMap.get(prompt);
    }


}
