package com.example.overwatch;

public class ShieldAbility extends HealAbility {
    //This ability can only be used once
    boolean hasBeenUsed;

    public ShieldAbility(String name, int iconResourceID, String actionVerb, int healValue) {
        super(name, iconResourceID, actionVerb, healValue);
        this.hasBeenUsed = false;
    }

    public boolean castAbility(Hero target) {
        if(!hasBeenUsed) {
            if (target.isAlive() && !target.isFullHP()) {
                if (super.getAbilityValue() > (target.getMaxHP() - target.getCurrentHP())) {
                    target.setCurrentHP(target.getMaxHP());
                    super.setActionVerb("shield (0 uses left)");
                    hasBeenUsed = true;
                    return true;
                } else {
                    target.setCurrentHP(target.getCurrentHP() + super.getAbilityValue());
                    super.setActionVerb("shield (0 uses left)");
                    hasBeenUsed = true;
                    return true;
                }
            }
            //Ability fails to cast if target is fullHP
            return false;
        } else {
            //Ability fails to cast if used more than once
            return false;
        }
    }
}
