package com.example.overwatch;

public class HealAbility extends Ability {
    private int healValue;
    private String actionVerb;

    public HealAbility(String name, int iconResourceID, String actionVerb, int healValue) {
        super(name, iconResourceID);
        this.actionVerb = actionVerb;
        this.healValue = healValue;
        setTargetsAllies(true);
    }

    public boolean castAbility(Hero target) {
        if(target.isAlive() && !target.isFullHP()) {
            if(healValue > (target.getMaxHP() - target.getCurrentHP())) {
                target.setCurrentHP(target.getMaxHP());
                return true;
            } else {
                target.setCurrentHP(target.getCurrentHP() + healValue);
                return true;
            }
        }
        //Ability fails to cast if target is fullHP
        return false;
    }

    public int getAbilityValue() {
        return healValue;
    }

    public String getActionVerb() {
        return actionVerb;
    }

    public void setActionVerb(String actionVerb) { this.actionVerb = actionVerb; }
}
