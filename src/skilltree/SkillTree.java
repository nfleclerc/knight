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

    private List<Skill> skills;



    public SkillTree(Player player) {

        skills = new ArrayList<>();

        Movement m1 = new Movement(player, 1.2, null, 0);
        Attack a1 = new Attack(player, 1.2, null, 1);
        Defense d1 = new Defense(player, 1.2, null, 2);
        Blacksmith b1 = new Blacksmith(player, null, 3);

        Movement m2 = new Movement(player, 1.4, m1, 4);
        Attack a2 = new Attack(player, 1.4, a1, 5);
        Defense d2 = new Defense(player, 1.4, d1, 6);
        Blacksmith b2 = new Blacksmith(player, b1, 7);

        Movement m3 = new Movement(player, 1.6, m2, 8);
        Attack a3 = new Attack(player, 1.6, a2, 9);
        Defense d3 = new Defense(player, 1.6, d2, 10);
        Blacksmith b3 = new Blacksmith(player, b2, 11);

        Movement m4 = new Movement(player, 1.8, m3, 12);
        Attack a4 = new Attack(player, 1.8, a3, 13);
        Defense d4 = new Defense(player, 1.8, d3, 14);
        Blacksmith b4 = new Blacksmith(player, b3, 15);

        Movement m5 = new Movement(player, 2.0, m4, 16);
        Attack a5 = new Attack(player, 2.0, a4, 17);
        Defense d5 = new Defense(player, 2.0, d4, 18);
        Blacksmith b5 = new Blacksmith(player, b4, 19);

        skills.add(m1);
        skills.add(a1);
        skills.add(d1);
        skills.add(b1);

        skills.add(m2);
        skills.add(a2);
        skills.add(d2);
        skills.add(b2);

        skills.add(m3);
        skills.add(a3);
        skills.add(d3);
        skills.add(b3);

        skills.add(m4);
        skills.add(a4);
        skills.add(d4);
        skills.add(b4);

        skills.add(m5);
        skills.add(a5);
        skills.add(d5);
        skills.add(b5);

    }

    public Skill getSkillAt(int index){
        return skills.get(index);
    }

    public List<Skill> getSkills(){
        return this.skills;
    }

}
