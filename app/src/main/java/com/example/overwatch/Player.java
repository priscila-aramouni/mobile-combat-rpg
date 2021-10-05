package com.example.overwatch;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private ArrayList<Hero> selectedHeroes = new ArrayList<>();
    private int heroCount = 0;

    public ArrayList<Hero> getSelectedHeroes() {
        return selectedHeroes;
    }

    public void addHero(Hero hero) {
        this.selectedHeroes.add(hero);
    }

    public void removeHero(Hero hero) {
        this.selectedHeroes.remove(hero);
    }

    public int getHeroCount() {
        return heroCount;
    }

    public void incrementHeroCount() {
        heroCount++;
    }
    
    public void decrementHeroCount() {
        heroCount--;
    }
}
