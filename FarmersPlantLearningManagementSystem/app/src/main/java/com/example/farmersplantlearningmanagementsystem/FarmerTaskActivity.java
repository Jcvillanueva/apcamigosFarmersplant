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

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.Feedbacks;
import com.example.farmersplantlearningmanagementsystem.models.Tasks;

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

public class FarmerTaskActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    FarmerTaskItemAdapter farmerTaskItemAdapter;
    ListView lvfarmerTasks;
    ArrayList<Tasks> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_task);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        lvfarmerTasks = findViewById(R.id.lvfarmerTasks);
        tasks = new ArrayList<>();

        ImageButton profileBtn3 = findViewById(R.id.profileButton3);
        profileBtn3.setOnClickListener(view -> {
            Intent FarmerProfileActivityIntent = new Intent(FarmerTaskActivity.this, FarmerProfileActivity.class);
            startActivity(FarmerProfileActivityIntent);
        });

        ImageButton moduleBtn3 = findViewById(R.id.moduleButton3);
        moduleBtn3.setOnClickListener(view -> {
            Intent FarmerModuleActivityIntent = new Intent(FarmerTaskActivity.this, FarmerModuleActivity.class);
            startActivity(FarmerModuleActivityIntent);
        });

        ImageButton taskBtn3 = findViewById(R.id.taskButton3);
        taskBtn3.setOnClickListener(view -> {
            Intent FarmerTaskActivityIntent = new Intent(FarmerTaskActivity.this, FarmerTaskActivity.class);
            startActivity(FarmerTaskActivityIntent);
        });

        ImageButton gradeBtn3 = findViewById(R.id.gradeButton3);
        gradeBtn3.setOnClickListener(view -> {
            Intent FarmerGradeActivityIntent = new Intent(FarmerTaskActivity.this, FarmerGradeActivity.class);
            startActivity(FarmerGradeActivityIntent);
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Fetching Task");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        this.fetchTasks();
    }

    private void fetchTasks() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/farmer/task/get/all/" + user_id)
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        FarmerTaskActivity.this,
                        "An error has occurred. Please try again.",
                        Toast.LENGTH_LONG
                    ).show();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {
                    progress.dismiss();
                    try {
                        ResponseBody responseBody = response.body();
                        JSONObject joProducts = new JSONObject(responseBody.string());

                        if (!response.isSuccessful()) {
                            runOnUiThread(() -> {
                                try {
                                    Toast.makeText(
                                        FarmerTaskActivity.this,
                                        joProducts.getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            JSONArray dataArr = joProducts
                                .getJSONObject("data")
                                .getJSONArray("tasks");
                            JSONArray taskUploadedIds = joProducts
                                .getJSONObject("data")
                                .getJSONArray("uploaded");
                            Log.i("Tasks", dataArr.toString());
                            Log.i("UploadedIds", taskUploadedIds.toString());

                            tasks.clear();
                            for (int i = 0; i < dataArr.length(); i++) {
                                String submitted_at = "";
                                for (int y = 0; y < taskUploadedIds.length(); y++) {
                                    if (
                                        dataArr.getJSONObject(i).getInt("id") ==
                                        taskUploadedIds.getJSONObject(y).getInt("task_id")
                                    ) {
                                        submitted_at = taskUploadedIds.getJSONObject(y).getString("created_at");
                                        break;
                                    }
                                }
                                tasks.add(new Tasks(
                                    dataArr.getJSONObject(i).getInt("id"),
                                    dataArr.getJSONObject(i).getInt("user_id"),
                                    dataArr.getJSONObject(i).getString("name"),
                                    taskUploadedIds.toString().contains(
                                        "\"task_id\":" + dataArr.getJSONObject(i).getInt("id") + ","
                                    ),
                                    submitted_at,
                                    dataArr.getJSONObject(i).getString("created_at"),
                                    dataArr.getJSONObject(i).getString("updated_at")
                                ));
                            }
                            farmerTaskItemAdapter = new FarmerTaskItemAdapter(
                                FarmerTaskActivity.this,
                                R.layout.fragment_farmer_task,
                                tasks
                            );
                            runOnUiThread(() -> lvfarmerTasks.setAdapter(farmerTaskItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            FarmerTaskActivity.this,
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
                FarmerTaskActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }

}