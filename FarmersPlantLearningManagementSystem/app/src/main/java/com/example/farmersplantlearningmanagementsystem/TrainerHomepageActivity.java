package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TrainerHomepageActivity extends AppCompatActivity {

    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    public static final String FULLNAME = "fullname";
    public static final String PLACE_ENROLLED = "placeEnrolled";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_homepage);

        ImageButton profileBtn6 = findViewById(R.id.profileButton6);
        profileBtn6.setOnClickListener(view -> {
            Intent ProfileActivityIntent = new Intent(TrainerHomepageActivity.this, TrainerProfileActivity.class);
            startActivity(ProfileActivityIntent);
        });

        ImageButton moduleBtn6 = findViewById(R.id.moduleButton6);
        moduleBtn6.setOnClickListener(view -> {
            Intent ModuleActivityIntent = new Intent(TrainerHomepageActivity.this, TrainerModuleActivity.class);
            startActivity(ModuleActivityIntent);
        });

        ImageButton taskBtn6 = findViewById(R.id.taskButton6);
        taskBtn6.setOnClickListener(view -> {
            Intent TaskActivityIntent = new Intent(TrainerHomepageActivity.this, TrainerTaskActivity.class);
            startActivity(TaskActivityIntent);
        });

        ImageButton gradeBtn6 = findViewById(R.id.gradeButton6);
        gradeBtn6.setOnClickListener(view -> {
            Intent GradeActivityIntent = new Intent(TrainerHomepageActivity.this, TrainerGradeActivity.class);
            startActivity(GradeActivityIntent);
        });

        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(view -> {
            Intent trainerLoginIntent = new Intent(TrainerHomepageActivity.this, TrainerLoginActivity.class);

            SharedPreferences sharedPreferences = getSharedPreferences(
                    SHARED_PREFS_TOKEN,
                    MODE_PRIVATE
            );
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USERID, null);
            editor.putString(FULLNAME, null);
            editor.putString(PLACE_ENROLLED, null);
            editor.putString(USERNAME, null);
            editor.putString(EMAIL, null);
            editor.putString(PASSWORD, null);
            editor.apply();

            startActivity(trainerLoginIntent);
            finish();
        });

    }
}