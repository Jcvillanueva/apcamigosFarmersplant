package com.example.farmersplantlearningmanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class TrainerTaskFarmersActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    TaskUsersItemAdapter taskUsersItemAdapter;
    ListView lvTaskFarmers;
    ArrayList<TaskFarmers> taskFarmers;
    int task_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_task_farmers);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        Intent intent = getIntent();
        task_id = intent.getIntExtra("task_id", 0);

        lvTaskFarmers = findViewById(R.id.lvTaskFarmers);
        taskFarmers = new ArrayList<>();

        progress = new ProgressDialog(this);
        progress.setTitle("Fetching farmers");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        if (task_id > 0) {
            this.fetchTaskFarmers();
        } else {
            Toast.makeText(
                TrainerTaskFarmersActivity.this,
                "An error has occured. No Task ID value.",
                Toast.LENGTH_LONG
            ).show();
        }

        lvTaskFarmers.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent trainerTaskFarmerImagesActivity = new Intent(
                this,
                TrainerTaskFarmerImagesActivity.class
            );
            trainerTaskFarmerImagesActivity.putExtra(
                "task_id",
                task_id
            );
            trainerTaskFarmerImagesActivity.putExtra(
                "farmer_user_id",
                taskFarmers.get(i).getUser_id()
            );
            trainerTaskFarmerImagesActivity.putExtra(
                    "farmer_name",
                    taskFarmers.get(i).getFull_name()
            );
            trainerTaskFarmerImagesActivity.putExtra(
                    "farmer_location",
                    taskFarmers.get(i).getPlace_enrolled()
            );
            trainerTaskFarmerImagesActivity.putExtra(
                    "farmer_bdate",
                    taskFarmers.get(i).getBdate()
            );
            startActivity(trainerTaskFarmerImagesActivity);
        });
    }

    private void fetchTaskFarmers() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/task/farmers/all/" + user_id + "/" + task_id)
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        TrainerTaskFarmersActivity.this,
                        "An error has occured. Please try again.",
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
                                        TrainerTaskFarmersActivity.this,
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
                                JSONObject jouser = dataArr.getJSONObject(i).getJSONObject("user");
                                taskFarmers.add(new TaskFarmers(
                                    jouser.getInt("id"),
                                    jouser.getString("full_name"),
                                    jouser.getString("place_enrolled"),
                                    jouser.getString("bdate")
                                ));
                            }
                            taskUsersItemAdapter = new TaskUsersItemAdapter(
                                TrainerTaskFarmersActivity.this,
                                R.layout.fragment_task_farmer,
                                taskFarmers
                            );
                            runOnUiThread(() -> lvTaskFarmers.setAdapter(taskUsersItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            TrainerTaskFarmersActivity.this,
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
                TrainerTaskFarmersActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}