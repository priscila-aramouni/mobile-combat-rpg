package com.example.overwatch;

import java.io.Serializable;

public class Ability implements Serializable {
    String name;
    int iconResourceID;
    boolean targetsAllies;

    public Ability(String name, int iconResourceID) {
        this.name = name;
        this.iconResourceID = iconResourceID;
    }

    public int getIconResourceID() {
        return iconResourceID;
    }

    public boolean targetsAllies() {
        return  targetsAllies;
    }

    public void setTargetsAllies(boolean bool) {
        targetsAllies = bool;
    }

    public boolean castAbility(Hero target) { return false; }

    public String getActionVerb() { return null; }

    public int getAbilityValue() { return 0; }
}
