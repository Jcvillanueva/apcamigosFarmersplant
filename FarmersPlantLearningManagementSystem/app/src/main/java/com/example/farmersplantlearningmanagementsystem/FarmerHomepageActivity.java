package com.example.farmersplantlearningmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FarmerHomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_homepage);

        ImageButton profileBtn = (ImageButton) findViewById(R.id.profileButton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FarmerProfileActivityIntent = new Intent(FarmerHomepageActivity.this, FarmerProfileActivity.class);
                startActivity(FarmerProfileActivityIntent);
            }
        });

        ImageButton moduleBtn = (ImageButton) findViewById(R.id.moduleButton);
        moduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FarmerModuleActivityIntent = new Intent(FarmerHomepageActivity.this, FarmerModuleActivity.class);
                startActivity(FarmerModuleActivityIntent);
            }
        });

        ImageButton taskBtn = (ImageButton) findViewById(R.id.taskButton);
        taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FarmerTaskActivityIntent = new Intent(FarmerHomepageActivity.this, FarmerTaskActivity.class);
                startActivity(FarmerTaskActivityIntent);
            }
        });

        ImageButton gradeBtn = (ImageButton) findViewById(R.id.gradeButton);
        gradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FarmerGradeActivityIntent = new Intent(FarmerHomepageActivity.this, FarmerGradeActivity.class);
                startActivity(FarmerGradeActivityIntent);
            }
        });
    }
}