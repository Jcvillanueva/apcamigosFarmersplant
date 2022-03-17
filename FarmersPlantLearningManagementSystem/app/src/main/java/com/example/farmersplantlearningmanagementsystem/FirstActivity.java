package com.example.farmersplantlearningmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ImageButton farmerBtn = (ImageButton) findViewById(R.id.farmerBtn);
        farmerBtn.setOnClickListener(view -> {
            Intent farmerLoginActivityIntent = new Intent(FirstActivity.this, FarmerLoginActivity.class);
                startActivity(farmerLoginActivityIntent);
        });

        ImageButton trainerBtn = (ImageButton) findViewById(R.id.trainerBtn);
        trainerBtn.setOnClickListener(view -> {
            Intent traimerLoginActivityIntent = new Intent(FirstActivity.this, TrainerLoginActivity.class);
            startActivity(traimerLoginActivityIntent);
        });
    }
}