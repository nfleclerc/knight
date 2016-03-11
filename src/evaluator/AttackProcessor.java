/*
 * Copyright (c) 2016.
 */

package evaluator;

import gameStates.levels.LevelState;
import main.GamePanel;

/**
 * Created by nathaniel on 3/10/16.
 */
public class AttackProcessor {

    private LevelState levelState;

    public AttackProcessor(LevelState levelState){
        this.levelState = levelState;
        if (levelState.getPlayer().enemyInRange(levelState.getEnemies())){
            levelState.getGamePanel().setProcessingAttack(true);
            new CodeWindow(this);
        } else {
            levelState.getPlayer().setAttacking();
        }
    }

    public synchronized void processClick() {
        levelState.getGamePanel().setProcessingAttack(false);
        synchronized (levelState.getGamePanel()) {
            levelState.getGamePanel().notify();
        }
        levelState.getPlayer().setAttacking();
    }
}
