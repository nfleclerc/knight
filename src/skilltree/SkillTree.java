/*
 * Copyright (c) 2016.
 */

package skilltree;

import entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathaniel on 3/29/16.
 */
public class SkillTree {

    private List<Skill> skills;



    public SkillTree(Player player) {

        skills = new ArrayList<>();

        Movement m1 = new Movement(player, 1.05, null, 0,
                "/skill_buttons/movement/movement_1_selected.gif",
                "/skill_buttons/movement/movement_1_unselected.gif",
                "/skill_buttons/movement/movement_1_locked.gif");
        Attack a1 = new Attack(player, 0.15, null, 1,
                "/skill_buttons/attack/attack_1_selected.gif",
                "/skill_buttons/attack/attack_1_unselected.gif",
                "/skill_buttons/attack/attack_1_locked.gif");
        Defense d1 = new Defense(player, 0.15, null, 2,
                "/skill_buttons/defense/defense_1_selected.gif",
                "/skill_buttons/defense/defense_1_unselected.gif",
                "/skill_buttons/defense/defense_1_locked.gif");

        Movement m2 = new Movement(player, 1.10, m1, 3,
                "/skill_buttons/movement/movement_2_selected.gif",
                "/skill_buttons/movement/movement_2_unselected.gif",
                "/skill_buttons/movement/movement_2_locked.gif");
        Attack a2 = new Attack(player, 0.6050, a1,4,
                "/skill_buttons/attack/attack_2_selected.gif",
                "/skill_buttons/attack/attack_2_unselected.gif",
                "/skill_buttons/attack/attack_2_locked.gif");
        Defense d2 = new Defense(player, 0.6050, d1, 5,
                "/skill_buttons/defense/defense_2_selected.gif",
                "/skill_buttons/defense/defense_2_unselected.gif",
                "/skill_buttons/defense/defense_2_locked.gif");

        Movement m3 = new Movement(player, 1.15, m2, 6,
                "/skill_buttons/movement/movement_3_selected.gif",
                "/skill_buttons/movement/movement_3_unselected.gif",
                "/skill_buttons/movement/movement_3_locked.gif");
        Attack a3 = new Attack(player, 0.605, a2, 7,
                "/skill_buttons/attack/attack_3_selected.gif",
                "/skill_buttons/attack/attack_3_unselected.gif",
                "/skill_buttons/attack/attack_3_locked.gif");
        Defense d3 = new Defense(player, 0.605, d2, 8,
                "/skill_buttons/defense/defense_3_selected.gif",
                "/skill_buttons/defense/defense_3_unselected.gif",
                "/skill_buttons/defense/defense_3_locked.gif");

        Movement m4 = new Movement(player, 1.20, m3, 9,
                "/skill_buttons/movement/movement_4_selected.gif",
                "/skill_buttons/movement/movement_4_unselected.gif",
                "/skill_buttons/movement/movement_4_locked.gif");
        Attack a4 = new Attack(player, 0.60, a3, 10,
                "/skill_buttons/attack/attack_4_selected.gif",
                "/skill_buttons/attack/attack_4_unselected.gif",
                "/skill_buttons/attack/attack_4_locked.gif");
        Defense d4 = new Defense(player, 0.60, d3, 11,
                "/skill_buttons/defense/defense_4_selected.gif",
                "/skill_buttons/defense/defense_4_unselected.gif",
                "/skill_buttons/defense/defense_4_locked.gif");

        Movement m5 = new Movement(player, 1.25, m4, 12,
                "/skill_buttons/movement/movement_5_selected.gif",
                "/skill_buttons/movement/movement_5_unselected.gif",
                "/skill_buttons/movement/movement_5_locked.gif");
        Attack a5 = new Attack(player, 0.75, a4, 13,
                "/skill_buttons/attack/attack_5_selected.gif",
                "/skill_buttons/attack/attack_5_unselected.gif",
                "/skill_buttons/attack/attack_5_locked.gif");
        Defense d5 = new Defense(player, 0.75, d4, 14,
                "/skill_buttons/defense/defense_5_selected.gif",
                "/skill_buttons/defense/defense_5_unselected.gif",
                "/skill_buttons/defense/defense_5_locked.gif");

        skills.add(m1);
        skills.add(a1);
        skills.add(d1);

        skills.add(m2);
        skills.add(a2);
        skills.add(d2);

        skills.add(m3);
        skills.add(a3);
        skills.add(d3);

        skills.add(m4);
        skills.add(a4);
        skills.add(d4);

        skills.add(m5);
        skills.add(a5);
        skills.add(d5);

    }

    public Skill getSkillAt(int index){
        return skills.get(index);
    }

    public List<Skill> getSkills(){
        return this.skills;
    }

}
