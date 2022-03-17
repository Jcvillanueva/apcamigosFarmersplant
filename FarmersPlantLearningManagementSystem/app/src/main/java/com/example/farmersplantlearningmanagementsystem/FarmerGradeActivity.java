package com.example.farmersplantlearningmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.Feedbacks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FarmerGradeActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    TrainerFeedbackItemAdapter trainerFeedbackItemAdapter;
    ListView lvFarmerGrades;
    ArrayList<Feedbacks> feedbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_grade);

        ImageButton profileBtn5 = findViewById(R.id.profileButton5);
        profileBtn5.setOnClickListener(view -> {
            Intent FarmerProfileActivityIntent =
                    new Intent(
                            FarmerGradeActivity.this,
                            FarmerProfileActivity.class);
            startActivity(FarmerProfileActivityIntent);
        });

        ImageButton moduleBtn5 = findViewById(R.id.moduleButton5);
        moduleBtn5.setOnClickListener(view -> {
            Intent FarmerModuleActivityIntent =
                    new Intent(
                            FarmerGradeActivity.this,
                            FarmerModuleActivity.class);
            startActivity(FarmerModuleActivityIntent);
        });

        ImageButton taskBtn5 = findViewById(R.id.taskButton5);
        taskBtn5.setOnClickListener(view -> {
            Intent FarmerTaskActivityIntent =
                    new Intent(
                            FarmerGradeActivity.this,
                            FarmerTaskActivity.class);
            startActivity(FarmerTaskActivityIntent);
        });

        ImageButton gradeBtn5 = findViewById(R.id.gradeButton5);
        gradeBtn5.setOnClickListener(view -> {
            Intent FarmerGradeActivityIntent =
                    new Intent(
                            FarmerGradeActivity.this,
                            FarmerGradeActivity.class);
            startActivity(FarmerGradeActivityIntent);
        });

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        feedbacks = new ArrayList<>();

        lvFarmerGrades = findViewById(R.id.lvFarmerGrades);


        progress = new ProgressDialog(this);
        progress.setTitle("Fetching farmers");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        this.fetchFarmerGradedTasks();
    }

    private void fetchFarmerGradedTasks() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/feedback/" + user_id)
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        FarmerGradeActivity.this,
                        "An error has occurred. Please try again.",
                        Toast.LENGTH_LONG
                    ).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    progress.dismiss();
                    try {
                        ResponseBody responseBody = response.body();
                        JSONObject jo = new JSONObject(responseBody.string());

                        if (!response.isSuccessful()) {
                            runOnUiThread(() -> {
                                try {
                                    Toast.makeText(
                                        FarmerGradeActivity.this,
                                        jo.getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            JSONArray dataArr = jo.getJSONArray("data");

                            feedbacks.clear();
                            for (int i = 0; i < dataArr.length(); i++) {
                                feedbacks.add(new Feedbacks(
                                    dataArr.getJSONObject(i).getInt("user_id"),
                                    dataArr.getJSONObject(i).getInt("task_id"),
                                    dataArr.getJSONObject(i).getJSONObject("task").getString("name"),
                                    dataArr.getJSONObject(i).getString("feedback"),
                                    dataArr.getJSONObject(i).getString("created_at")
                                ));
                            }
                            trainerFeedbackItemAdapter = new TrainerFeedbackItemAdapter(
                                FarmerGradeActivity.this,
                                R.layout.fragment_trainer_feedback,
                                feedbacks
                            );
                            runOnUiThread(() -> lvFarmerGrades.setAdapter(trainerFeedbackItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            FarmerGradeActivity.this,
                            "An error has occurred. Please try again.",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            });
        } catch (Exception e) {
            progress.dismiss();
            Log.e("Ex", Log.getStackTraceString(e));
            Toast.makeText(
                FarmerGradeActivity.this,
                "An error has occurred. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}