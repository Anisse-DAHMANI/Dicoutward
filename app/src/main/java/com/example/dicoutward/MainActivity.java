package com.example.dicoutward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        final ImageButton crafting = findViewById(R.id.crafting);
        crafting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityCrafting(v);
            }
        });
    }

    public void openActivityCrafting(View view){
        Intent intent = new Intent(this, CraftingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openActivityEnnemies(View view){
        Intent intent = new Intent(this, EnnemiesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}