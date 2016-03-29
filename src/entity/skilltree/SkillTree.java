/*
 * Copyright (c) 2016.
 */

package entity.skilltree;

import entity.Player;

import java.util.List;

/**
 * Created by nathaniel on 3/29/16.
 */
public class SkillTree {

    private final Player player;
    private List<Skill> blacksmith;
    private List<Skill> maurader;
    private List<Skill> warrior;
    private List<Skill> juggernaut;

    public SkillTree(Player player) {
        this.player = player;
    }

}
