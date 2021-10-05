package com.example.overwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class HeroSelectionActivity extends AppCompatActivity {

                    /*~~~~~~~~~~ Variable/Object declaration ~~~~~~~~~~*/
    Player player1 = new Player();
    Player player2 = new Player();
    Hero p1ClickedHero;
    Hero p2ClickedHero;
    Button readyButton;

    RecyclerView p1Recycler;
    RecyclerView p2Recycler;
    HeroAdapter p1Adapter;
    HeroAdapter p2Adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_selection);

        hideSystemUI();
        //This prevents the system UI from re-appearing when the user uses the keyboard
        final View activityRootView = findViewById(R.id.root_layout_heroselection);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    hideSystemUI();
                }
            }
        });

                     /*~~~~~~~~~~ Variable/Object initialization ~~~~~~~~~~*/
        readyButton = findViewById(R.id.ready_button);

        //Mercy abilities
        HealAbility healingBeam = new HealAbility("Healing Beam",
                R.drawable.mercy_healing_beam, "heal", +35);
        DamageAbility caduceusBlaster = new DamageAbility("Caduceus Blaster",
                R.drawable.mercy_caduceus_blaster, "shoot at", -20);

        //Soldier: 76 abilities
        HealAbility bioticField = new HealAbility("Biotic Field",
                R.drawable.soldier76_biotic_field, "heal", +10);
        DamageAbility pulseRifle = new DamageAbility("Pulse Rifle",
                R.drawable.soldier76_pulse_rifle, "shoot at", -55);

        //Roadhog abilities
        HealAbility takeABreather = new HealAbility("Take a Breather",
                R.drawable.roadhog_take_a_breather, "heal", +10);
        DamageAbility scrapGun = new DamageAbility("Scrap Gun Blast",
                R.drawable.roadhog_scrapgun, "hook", -40);

        //Zenyatta abilities
        HealAbility orbOfHarmony = new HealAbility("Orb of Harmony",
                R.drawable.zen_orb_of_harmony, "throw healing orb at", +30);
        DamageAbility orbOfDiscord = new DamageAbility("Orb of Discord",
                R.drawable.zen_orb_of_discord, "throw discord orb at", -40);

        //Widowmaker abilities
        DamageAbility widowsKiss = new DamageAbility("Widow's Kiss",
                R.drawable.widowmaker_widows_kiss, "shoot", -45);
        HeadshotAbility infraSight = new HeadshotAbility("Infra-Sight",
                R.drawable.widowmaker_infrasight, "Headshot (20% chance to deal double damage) ", -35, 30);

        //Reinhardt abilities
        ShieldAbility shieldBarrier = new ShieldAbility("Shield Barrier",
                R.drawable.reinhardt_shield_barrier, "shield (1 use left)", +80);
        DamageAbility rocketHammer = new DamageAbility("Rocket Hammer",
                R.drawable.reinhardt_rocket_hammer, "swing at", -45);

        //Creating list of heroes
        final ArrayList<Hero> heroList = new ArrayList<>();
        heroList.add(new Hero("Mercy", "Support",
                R.drawable.mercy_pixel, 200, healingBeam, caduceusBlaster));

        heroList.add(new Hero("Soldier: 76", "Damage",
                R.drawable.soldier76_pixel, 200, bioticField, pulseRifle));

        heroList.add(new Hero("Roadhog", "Tank",
                R.drawable.roadhog_pixel, 400, takeABreather, scrapGun));

        heroList.add(new Hero("Zenyatta", "Support",
                R.drawable.zenyatta_pixel, 200, orbOfHarmony, orbOfDiscord));

        heroList.add(new Hero("Widowmaker", "Damage",
                R.drawable.widowmaker_pixel, 200, widowsKiss, infraSight));

        heroList.add(new Hero("Reinhardt", "Tank",
                R.drawable.reinhardt_pixel, 450, shieldBarrier, rocketHammer));

        /* Populating player 1's recycler view */
        p1Recycler = findViewById(R.id.player1_hero_recycler);
        p1Recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        p1Adapter = new HeroAdapter(heroList);

        p1Recycler.setLayoutManager(layoutManager);
        p1Recycler.setAdapter(p1Adapter);

        //This handles click events in player 1's recycler view
        p1Adapter.setOnItemClickListener(new HeroAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                p1ClickedHero = heroList.get(position);
                handleHeroSelection(player1, p1ClickedHero);
            }
        });

        /* Populating player 2's recycler view */
        p2Recycler = findViewById(R.id.player2_hero_recycler);
        p2Recycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        p2Adapter = new HeroAdapter(heroList);

        p2Recycler.setLayoutManager(layoutManager);
        p2Recycler.setAdapter(p2Adapter);

        //This handles click events in player 2's recycler view
        p2Adapter.setOnItemClickListener(new HeroAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                p2ClickedHero = heroList.get(position);
                handleHeroSelection(player2, p2ClickedHero);
            }
        });
    }

    public void hideSystemUI(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    public void handleHeroSelection(Player player, Hero clickedHero){
        /* If clickedHero is NOT already selected,
        add it to player's selectedHeroes and increment player's heroCount */
        if(!player.getSelectedHeroes().contains(clickedHero)){
            player.addHero(clickedHero);
            player.incrementHeroCount();
            toggleReadyButtonState();
        } else {
            /* If clickedHero is already selected,
            remove it from player's selectedHeroes and decrement player's heroCount */
            player.getSelectedHeroes().remove(clickedHero);
            player.decrementHeroCount();
            toggleReadyButtonState();
        }
    }
    
    //Enable readyButton only if both players have selected 2 heroes. Otherwise, disable it.
    public void toggleReadyButtonState() {
        if(player1.getHeroCount() == 2 && player2.getHeroCount() == 2)
            readyButton.setEnabled(true);
        else
            readyButton.setEnabled(false);
    }

    public void goToGame(View view) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        intent.putExtra("Player1", player1);
        intent.putExtra("Player2", player2);

        startActivity(intent);
    }
}
