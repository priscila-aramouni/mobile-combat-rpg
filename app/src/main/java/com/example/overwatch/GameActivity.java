package com.example.overwatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

                    /*~~~~~~~~~~ Variable/Object declaration ~~~~~~~~~~*/
    int round;
    String winner;
    AlertDialog gameOverDialog;
    SharedPreferences sharedPreferences;
    int player1Wins, player2Wins;

    Player player1, player2;
    Hero p1Hero1, p1Hero2, p2Hero1, p2Hero2;
    Hero targetHero1, targetHero2;
    TextView p1text, p2text;

    TextView p1Hero1HP, p1Hero2HP, p2Hero1HP, p2Hero2HP;
    TextView p1Hero1Name, p1Hero2Name, p2Hero1Name, p2Hero2Name;
    ImageView p1Hero1Icon, p1Hero2Icon, p2Hero1Icon, p2Hero2Icon;
    ImageView p1Hero1Ability1Icon, p1Hero1Ability2Icon, p1Hero2Ability1Icon, p1Hero2Ability2Icon,
            p2Hero1Ability1Icon, p2Hero1Ability2Icon, p2Hero2Ability1Icon, p2Hero2Ability2Icon;

    LinearLayout p1Hero1Layout, p1Hero2Layout, p2Hero1Layout, p2Hero2Layout;
    TextView p1AbilityPromptText, p2AbilityPromptText, playerTargetPromptText;
    Button targetHero1Button, targetHero2Button;
    Ability activeAbility;
    Hero activeHero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        hideSystemUI();
        //This prevents the system UI from re-appearing when the user uses the keyboard
        final View activityRootView = findViewById(R.id.root_layout_game);
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
        Intent intent = getIntent();
        player1 = (Player)intent.getSerializableExtra("Player1");
        player2 = (Player)intent.getSerializableExtra("Player2");

        targetHero1Button = findViewById(R.id.target1_button);
        targetHero2Button = findViewById(R.id.target2_button);

        p1Hero1Layout = findViewById(R.id.p1_hero1_linear_layout);
        p1Hero2Layout = findViewById(R.id.p1_hero2_linear_layout);
        p2Hero1Layout = findViewById(R.id.p2_hero1_linear_layout);
        p2Hero2Layout = findViewById(R.id.p2_hero2_linear_layout);

        p1AbilityPromptText = findViewById(R.id.player1_ability_prompt);
        p2AbilityPromptText = findViewById(R.id.player2_ability_prompt);
        playerTargetPromptText = findViewById(R.id.player_target_prompt);

        p1text = findViewById(R.id.player1_text);
        p2text = findViewById(R.id.player2_text);

        //Assigning selectedHeroes to their corresponding player
        p1Hero1 = player1.getSelectedHeroes().get(0);
        p1Hero2 = player1.getSelectedHeroes().get(1);
        p2Hero1 = player2.getSelectedHeroes().get(0);
        p2Hero2 = player2.getSelectedHeroes().get(1);

        //Setting HP based on player selection
        p1Hero1HP = findViewById(R.id.p1_hero1_HP);
        p1Hero2HP = findViewById(R.id.p1_hero2_HP);
        p2Hero1HP = findViewById(R.id.p2_hero1_HP);
        p2Hero2HP = findViewById(R.id.p2_hero2_HP);
        updateHPViews();

        //Setting heroNames based on player selection
        p1Hero1Name = findViewById(R.id.p1_hero1_name);
        p1Hero2Name = findViewById(R.id.p1_hero2_name);
        p2Hero1Name = findViewById(R.id.p2_hero1_name);
        p2Hero2Name = findViewById(R.id.p2_hero2_name);
        p1Hero1Name.setText(p1Hero1.getName());
        p1Hero2Name.setText(p1Hero2.getName());
        p2Hero1Name.setText(p2Hero1.getName());
        p2Hero2Name.setText(p2Hero2.getName());

        //Setting heroIcons based on player selection
        p1Hero1Icon = findViewById(R.id.p1_hero1_icon);
        p1Hero2Icon = findViewById(R.id.p1_hero2_icon);
        p2Hero1Icon = findViewById(R.id.p2_hero1_icon);
        p2Hero2Icon = findViewById(R.id.p2_hero2_icon);
        p1Hero1Icon.setImageResource(p1Hero1.getPixelResourceID());
        p1Hero2Icon.setImageResource(p1Hero2.getPixelResourceID());
        p2Hero1Icon.setImageResource(p2Hero1.getPixelResourceID());
        p2Hero2Icon.setImageResource(p2Hero2.getPixelResourceID());

        //Setting abilityIcons based on player selection
        p1Hero1Ability1Icon = findViewById(R.id.p1_hero1_ability1);
        p1Hero1Ability2Icon = findViewById(R.id.p1_hero1_ability2);
        p1Hero2Ability1Icon = findViewById(R.id.p1_hero2_ability1);
        p1Hero2Ability2Icon = findViewById(R.id.p1_hero2_ability2);
        p2Hero1Ability1Icon = findViewById(R.id.p2_hero1_ability1);
        p2Hero1Ability2Icon = findViewById(R.id.p2_hero1_ability2);
        p2Hero2Ability1Icon = findViewById(R.id.p2_hero2_ability1);
        p2Hero2Ability2Icon = findViewById(R.id.p2_hero2_ability2);
        p1Hero1Ability1Icon.setImageResource(p1Hero1.getAbility1Icon());
        p1Hero1Ability2Icon.setImageResource(p1Hero1.getAbility2Icon());
        p1Hero2Ability1Icon.setImageResource(p1Hero2.getAbility1Icon());
        p1Hero2Ability2Icon.setImageResource(p1Hero2.getAbility2Icon());
        p2Hero1Ability1Icon.setImageResource(p2Hero1.getAbility1Icon());
        p2Hero1Ability2Icon.setImageResource(p2Hero1.getAbility2Icon());
        p2Hero2Ability1Icon.setImageResource(p2Hero2.getAbility1Icon());
        p2Hero2Ability2Icon.setImageResource(p2Hero2.getAbility2Icon());

        sharedPreferences = getSharedPreferences("playerStats", MODE_PRIVATE);

        player1Wins = sharedPreferences.getInt("player1Wins", 0);
        player2Wins = sharedPreferences.getInt("player2Wins", 0);

        round = 0;
        adjustUIBasedOnPlayerTurn();
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

    public void p1Hero1Ability1(View view) {
        activeHero = p1Hero1;
        activeAbility = activeHero.getAbility1();
        setP1TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p1Hero1Ability2(View view) {
        activeHero = p1Hero1;
        activeAbility = activeHero.getAbility2();
        setP1TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p1Hero2Ability1(View view) {
        activeHero = p1Hero2;
        activeAbility = activeHero.getAbility1();
        setP1TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p1Hero2Ability2(View view) {
        activeHero = p1Hero2;
        activeAbility = activeHero.getAbility2();
        setP1TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p2Hero1Ability1(View view) {
        activeHero = p2Hero1;
        activeAbility = activeHero.getAbility1();
        setP2TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p2Hero1Ability2(View view) {
        activeHero = p2Hero1;
        activeAbility = activeHero.getAbility2();
        setP2TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p2Hero2Ability1(View view) {
        activeHero = p2Hero2;
        activeAbility = activeHero.getAbility1();
        setP2TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void p2Hero2Ability2(View view) {
        activeHero = p2Hero2;
        activeAbility = activeHero.getAbility2();
        setP2TargetHeroes(activeAbility);
        modifyTargetButton(activeHero);
    }

    public void setP1TargetHeroes(Ability ability) {
        if(ability.targetsAllies()){
            targetHero1 = p1Hero1;
            targetHero2 = p1Hero2;
        } else {
            targetHero1 = p2Hero1;
            targetHero2 = p2Hero2;
        }
    }

    public void setP2TargetHeroes(Ability ability) {
        if(ability.targetsAllies()){
            targetHero1 = p2Hero1;
            targetHero2 = p2Hero2;
        } else {
            targetHero1 = p1Hero1;
            targetHero2 = p1Hero2;
        }
    }

    public void modifyTargetButton(Hero hero) {
        p1AbilityPromptText.setVisibility(View.INVISIBLE);
        p2AbilityPromptText.setVisibility(View.INVISIBLE);
        playerTargetPromptText.setVisibility(View.VISIBLE);

        targetHero1Button.setText(activeAbility.getActionVerb() + " "
                + (hero.equals(targetHero1)?"self":targetHero1.getName())
                + " ("+ (activeAbility.getAbilityValue() < 0?"":"+")+ activeAbility.getAbilityValue() + " hp)");
        targetHero1Button.setVisibility(View.VISIBLE);

        targetHero2Button.setText(activeAbility.getActionVerb() + " "
                + (hero.equals(targetHero2)?"self":targetHero2.getName())
                + " ("+ (activeAbility.getAbilityValue() < 0?"":"+")+ activeAbility.getAbilityValue() + " hp)");
        targetHero2Button.setVisibility(View.VISIBLE);
    }

    public void castAbilityOnTarget1(View view){
        if(!activeAbility.castAbility(targetHero1))
            Toast.makeText(getApplicationContext(), "Cannot apply ability on " + targetHero1.getName() + "!", Toast.LENGTH_SHORT).show();
        else {
            updateHPViews();
            checkForDeadHeroes();
            checkForWinner();
            moveToNextRound();
        }
    }

    public void castAbilityOnTarget2(View view) {
        if(!activeAbility.castAbility(targetHero2))
            Toast.makeText(getApplicationContext(), "Cannot apply ability on " + targetHero2.getName() + "!", Toast.LENGTH_SHORT).show();
        else {
            updateHPViews();
            checkForDeadHeroes();
            checkForWinner();
            moveToNextRound();
        }
    }

    public void moveToNextRound() {
        playerTargetPromptText.setVisibility(View.INVISIBLE);
        targetHero1Button.setVisibility(View.INVISIBLE);
        targetHero2Button.setVisibility(View.INVISIBLE);
        round++;
        adjustUIBasedOnPlayerTurn();
    }

    public void adjustUIBasedOnPlayerTurn() {
        if(round%2 == 0) {
            //If round is even, Player 1's turn
            enableP1Abilities();
            disableP2Abilities();

            p1AbilityPromptText.setVisibility(View.VISIBLE);
            p2AbilityPromptText.setVisibility(View.INVISIBLE);

            playerTargetPromptText.setText("PLAYER 1: PICK A TARGET");
        } else {
            //If round is odd, Player 2's turn
            enableP2Abilities();
            disableP1Abilities();

            p2AbilityPromptText.setVisibility(View.VISIBLE);
            p1AbilityPromptText.setVisibility(View.INVISIBLE);

            playerTargetPromptText.setText("PLAYER 2: PICK A TARGET");
        }
    }

    public void disableP1Abilities() {
        p1Hero1Ability1Icon.setEnabled(false);
        p1Hero1Ability2Icon.setEnabled(false);
        p1Hero2Ability1Icon.setEnabled(false);
        p1Hero2Ability2Icon.setEnabled(false);
    }

    public void disableP2Abilities() {
        p2Hero1Ability1Icon.setEnabled(false);
        p2Hero1Ability2Icon.setEnabled(false);
        p2Hero2Ability1Icon.setEnabled(false);
        p2Hero2Ability2Icon.setEnabled(false);
    }

    public void enableP1Abilities() {
        if(p1Hero1.isAlive()) {
            p1Hero1Ability1Icon.setEnabled(true);
            p1Hero1Ability2Icon.setEnabled(true);
        }

        if(p1Hero2.isAlive()) {
            p1Hero2Ability1Icon.setEnabled(true);
            p1Hero2Ability2Icon.setEnabled(true);
        }
    }

    public void enableP2Abilities() {
        if(p2Hero1.isAlive()) {
            p2Hero1Ability1Icon.setEnabled(true);
            p2Hero1Ability2Icon.setEnabled(true);
        }

        if(p2Hero2.isAlive()) {
            p2Hero2Ability1Icon.setEnabled(true);
            p2Hero2Ability2Icon.setEnabled(true);
        }
    }

    public void updateHPViews() {
        p1Hero1HP.setText("HP: " + p1Hero1.getCurrentHP() + "/" + p1Hero1.getMaxHP());
        p1Hero2HP.setText("HP: " + p1Hero2.getCurrentHP() + "/" + p1Hero2.getMaxHP());
        p2Hero1HP.setText("HP: " + p2Hero1.getCurrentHP() + "/" + p2Hero1.getMaxHP());
        p2Hero2HP.setText("HP: " + p2Hero2.getCurrentHP() + "/" + p2Hero2.getMaxHP());
    }

    public void checkForWinner(){
        if(!p1Hero1.isAlive() && !p1Hero2.isAlive()) {
            winner = "Player 2";
            player2Wins++;
            displayGameOverDialogue();
        } else {
            if(!p2Hero1.isAlive() && !p2Hero2.isAlive()) {
                winner = "Player 1";
                player1Wins++;
                displayGameOverDialogue();
            }
        }
        sharedPreferences.edit().putInt("player1Wins", player1Wins).apply();
        sharedPreferences.edit().putInt("player2Wins", player2Wins).apply();
    }

    public void displayGameOverDialogue() {
        gameOverDialog = new AlertDialog.Builder(this)
                .setTitle("GAME OVER")
                . setIcon(R.drawable.ow_logo_dark)
                .setMessage(winner + " is the winner!")
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), HeroSelectionActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void checkForDeadHeroes() {
        //If hero is dead and his icon has not been faded/greyed out, update the icon
        if(!p1Hero1.isAlive() && p1Hero1Icon.getAlpha() != 128){
            updateDeadHeroIcon(p1Hero1Icon);
        }

        if(!p1Hero2.isAlive() && p1Hero2Icon.getAlpha() != 128){
            updateDeadHeroIcon(p1Hero2Icon);
        }

        if(!p2Hero1.isAlive() && p2Hero1Icon.getAlpha() != 128){
            updateDeadHeroIcon(p2Hero1Icon);
        }

        if(!p2Hero2.isAlive() && p2Hero2Icon.getAlpha() != 128){
            updateDeadHeroIcon(p2Hero2Icon);
        }
    }

    public void updateDeadHeroIcon(ImageView deadHeroImageView) { 
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        deadHeroImageView.setColorFilter(cf);
        deadHeroImageView.setImageAlpha(128);   // 128 = 0.5
    }
}

