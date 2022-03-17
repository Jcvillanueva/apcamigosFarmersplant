package com.example.farmersplantlearningmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreenActivity extends AppCompatActivity {

    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String FULLNAME = "fullname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        String fullname = shared.getString(FULLNAME, "");

        TextView txtvName = findViewById(R.id.txtvName);
        txtvName.setText(fullname);

        ImageButton startBtn2 = findViewById(R.id.startBtn2);
        startBtn2.setOnClickListener(view -> {
            Intent FarmerHomepageActivityIntent = new Intent(StartScreenActivity.this, FarmerHomepageActivity.class);
            startActivity(FarmerHomepageActivityIntent);
        });
    }
}