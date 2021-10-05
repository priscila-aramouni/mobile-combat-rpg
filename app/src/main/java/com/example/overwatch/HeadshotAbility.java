package com.example.overwatch;

import java.util.Random;

public class HeadshotAbility extends DamageAbility {
    //This type of ability has a chance to do double the damage
    private int doubleDamageChance;

    public HeadshotAbility(String name, int iconResourceID, String actionVerb, int baseDamage, int doubleDamageChance) {
        super(name, iconResourceID, actionVerb, baseDamage);
        this.doubleDamageChance = doubleDamageChance;
    }

    public boolean castAbility(Hero target) {
        if(target.isAlive()) {
            Random rand = new Random();
            int randInt = rand.nextInt(100);
            //If chances are in the hero's favor, it casts double the base damage
            if(randInt < doubleDamageChance) {
                if (Math.abs(super.getAbilityValue()*2) > target.getCurrentHP()) {
                    target.setCurrentHP(0);
                    return true;
                } else {
                    target.setCurrentHP(target.getCurrentHP() + super.getAbilityValue()*2);
                    return true;
                }
            } else {
                //If chances are not in the hero's favor, it casts the ability's base damage
                super.castAbility(target);
                return true;
            }
        }
        //Ability fails to cast if target is dead
        return false;
    }
}
