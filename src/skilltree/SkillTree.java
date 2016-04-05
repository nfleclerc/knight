/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;
import skilltree.blacksmith.Blacksmith;
import skilltree.juggernaut.Defense;
import skilltree.maurader.Movement;
import skilltree.warrior.Attack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathaniel on 3/29/16.
 */
public class SkillTree {

    private List<Skill> blacksmith;
    private List<Skill> maurader;
    private List<Skill> warrior;
    private List<Skill> juggernaut;

    public SkillTree(Player player) {

        blacksmith = new ArrayList<>();
        maurader = new ArrayList<>();
        warrior = new ArrayList<>();
        juggernaut = new ArrayList<>();

        Movement m1 = new Movement(player, 1.2, null);
        Movement m2 = new Movement(player, 1.4, m1);
        Movement m3 = new Movement(player, 1.6, m2);
        Movement m4 = new Movement(player, 1.8, m3);
        Movement m5 = new Movement(player, 2.0, m4);

        Attack a1 = new Attack(player, 1.2, null);
        Attack a2 = new Attack(player, 1.4, a1);
        Attack a3 = new Attack(player, 1.6, a2);
        Attack a4 = new Attack(player, 1.8, a3);
        Attack a5 = new Attack(player, 2.0, a4);

        Defense d1 = new Defense(player, 1.2, null);
        Defense d2 = new Defense(player, 1.4, d1);
        Defense d3 = new Defense(player, 1.6, d2);
        Defense d4 = new Defense(player, 1.8, d3);
        Defense d5 = new Defense(player, 2.0, d4);

        Blacksmith b1 = new Blacksmith(player, null);
        Blacksmith b2 = new Blacksmith(player, b1);
        Blacksmith b3 = new Blacksmith(player, b2);
        Blacksmith b4 = new Blacksmith(player, b3);
        Blacksmith b5 = new Blacksmith(player, b4);

    }

}
