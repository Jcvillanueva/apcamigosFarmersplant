package com.example.farmersplantlearningmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.TaskFarmers;

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

public class TrainerGradeActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    TaskUsersItemAdapter taskUsersItemAdapter;
    ListView lvtrainerViewGrade;
    ArrayList<TaskFarmers> taskFarmers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_grade);

        ImageButton profileBtn10 = findViewById(R.id.profileButton10);
        profileBtn10.setOnClickListener(view -> {
            Intent ProfileActivityIntent = new Intent(
                    TrainerGradeActivity.this,
                            TrainerProfileActivity.class);
            startActivity(ProfileActivityIntent);
        });

        ImageButton moduleBtn10 = findViewById(R.id.moduleButton10);
        moduleBtn10.setOnClickListener(view -> {
            Intent ModuleActivityIntent = new Intent(
                    TrainerGradeActivity.this,
                    TrainerModuleActivity.class);
            startActivity(ModuleActivityIntent);
        });

        ImageButton taskBtn10 = findViewById(R.id.taskButton10);
        taskBtn10.setOnClickListener(view -> {
            Intent TaskActivityIntent = new Intent(
                    TrainerGradeActivity.this,
                            TrainerTaskActivity.class);
            startActivity(TaskActivityIntent);
        });

        ImageButton gradeBtn10 = findViewById(R.id.gradeButton10);
        gradeBtn10.setOnClickListener(view -> {
            Intent GradeActivityIntent = new Intent(
                    TrainerGradeActivity.this,
                    TrainerGradeActivity.class);
            startActivity(GradeActivityIntent);
        });

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        lvtrainerViewGrade = findViewById(R.id.lvTrainerViewGrade);
        taskFarmers = new ArrayList<>();

        progress = new ProgressDialog(this);
        progress.setTitle("Fetching farmers");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        this.fetchGradedFarmers();

        lvtrainerViewGrade.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(
                this,
                TrainerGradedFarmerTasksActivity.class
            );
            intent.putExtra("farmer_user_id", taskFarmers.get(i).getUser_id());
            startActivity(intent);
        });
    }

    private void fetchGradedFarmers() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/graded")
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        TrainerGradeActivity.this,
                        "An error has occurred. Please try again.",
                        Toast.LENGTH_LONG
                    ).show();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    progress.dismiss();
                    try {
                        ResponseBody responseBody = response.body();
                        JSONObject jo = new JSONObject(responseBody.string());

                        if (!response.isSuccessful()) {
                            runOnUiThread(() -> {
                                try {
                                    Toast.makeText(
                                        TrainerGradeActivity.this,
                                        jo.getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            JSONArray dataArr = jo.getJSONArray("data");
                            Log.i("Modules", dataArr.toString());

                            taskFarmers.clear();
                            for (int i = 0; i < dataArr.length(); i++) {
                                taskFarmers.add(new TaskFarmers(
                                    dataArr.getJSONObject(i).getInt("id"),
                                    dataArr.getJSONObject(i).getString("full_name"),
                                    dataArr.getJSONObject(i).getString("place_enrolled"),
                                    dataArr.getJSONObject(i).getString("bdate")
                                ));
                            }
                            taskUsersItemAdapter = new TaskUsersItemAdapter(
                                TrainerGradeActivity.this,
                                R.layout.fragment_task_farmer,
                                taskFarmers
                            );
                            runOnUiThread(() -> lvtrainerViewGrade.setAdapter(taskUsersItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            TrainerGradeActivity.this,
                            "An error has occured. Please try again.",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            });
        } catch (Exception e) {
            progress.dismiss();
            Log.e("Ex", Log.getStackTraceString(e));
            Toast.makeText(
                TrainerGradeActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}