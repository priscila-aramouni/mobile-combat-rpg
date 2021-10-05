package com.example.overwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import android.graphics.Rect;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    TextView playerWinsText;
    int player1Wins, player2Wins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);

         hideSystemUI();
        //This prevents the system UI from re-appearing when the user uses the keyboard
        final View activityRootView = findViewById(R.id.root_layout_main);
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

    playerWinsText = findViewById(R.id.player_wins);

    sharedPreferences = getSharedPreferences("playerStats", MODE_PRIVATE);
    player1Wins = sharedPreferences.getInt("player1Wins", 0);
    player2Wins = sharedPreferences.getInt("player2Wins", 0);

    playerWinsText.setText("Player 1: " + player1Wins + (player1Wins==1?" win":" wins") + "\nPlayer 2: " + player2Wins + (player2Wins==1?" win":" wins"));

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

    public void goToHeroSelection(View view){
        Intent intent = new Intent(getApplicationContext(), HeroSelectionActivity.class);
        startActivity(intent);
    }
}


