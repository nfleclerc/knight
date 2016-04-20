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
 * Created by nathaniel on 4/16/16.
 */
public class TestBank {

    private final HashMap<String, String> testAnswerMap;
    private String intro;

    public TestBank(){
        this.testAnswerMap = new HashMap<>();

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

    public String getTest(Player player){
        return player.getXP() == 0 ? intro : getRandomTest();
    }

    private String getRandomTest() {
        List<String> keys = new ArrayList<>(testAnswerMap.keySet());
       return  keys.get(new Random().nextInt(testAnswerMap.size()));
    }

    public String getAnswer(String prompt){
        return testAnswerMap.get(prompt);
    }


}
