package com.example.overwatch;

import android.widget.Toast;

public class DamageAbility extends Ability {
    private int damageValue;
    private String actionVerb;

    public DamageAbility(String name, int iconResourceID, String actionVerb, int damageValue) {
        super(name, iconResourceID);
        this.actionVerb = actionVerb;
        this.damageValue = damageValue;
        setTargetsAllies(false);
    }

    public boolean castAbility(Hero target) {
        if(target.isAlive()) {
            if (Math.abs(damageValue) > target.getCurrentHP()) {
                target.setCurrentHP(0);
                return true;
            } else {
                target.setCurrentHP(target.getCurrentHP() + damageValue);
                return true;
            }
        }
        //Ability fails to cast if target is dead
        return false;
    }

    public String getActionVerb() {
        return actionVerb;
    }

    public int getAbilityValue() {
        return damageValue;
    }
}