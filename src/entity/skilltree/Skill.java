/*
 * Copyright (c) 2016.
 */

package entity.skilltree;

/**
 * Created by nathaniel on 3/29/16.
 */
public abstract class Skill {

    protected boolean active;
    protected Skill previous;

    protected void activate(){
        this.active = true;
    }

}
