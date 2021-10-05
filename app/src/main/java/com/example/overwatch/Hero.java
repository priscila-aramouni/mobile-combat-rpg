package com.example.overwatch;

import java.io.Serializable;

public class Hero implements Serializable {
    private String name;
    private String type;

    private int iconResourceID;
    private int currentHP;
    private final int maxHP;

    private boolean isAlive = true;
    private boolean isFullHP = true;

    private Ability ability1;
    private Ability ability2;

    public Hero(String name, String type, int iconResourceID, int currentHP, Ability ability1, Ability ability2) {
        this.name = name;
        this.type = type;
        this.iconResourceID = iconResourceID;
        this.currentHP = currentHP;
        this.maxHP = currentHP;
        this.ability1 = ability1;
        this.ability2 = ability2;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getPixelResourceID() {
        return iconResourceID;
    }

    public Ability getAbility1() {
        return ability1;
    }

    public Ability getAbility2() {
        return ability2;
    }

    public int getAbility1Icon() {
        return ability1.getIconResourceID();
    }

    public int getAbility2Icon() {
        return ability2.getIconResourceID();
    }

    public boolean isAlive() {
        return (currentHP > 0);
    }

    public boolean isFullHP() {
        return (currentHP == maxHP);
    }
}


