/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.levels.LevelState;

/**
 * Created by nathaniel on 3/10/16.
 */
public class AttackProcessor {

    public AttackProcessor(LevelState levelState){
        if (levelState.getPlayer().enemyInRange(levelState.getEnemies())){
            new CodeWindow();
        } else {
            levelState.getPlayer().setAttacking();
        }
    }

}
