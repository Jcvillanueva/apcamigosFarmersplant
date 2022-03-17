package com.example.farmersplantlearningmanagementsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TrainerTaskActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    TaskItemAdapter taskItemAdapter;
    ListView lvTasks;
    ArrayList<Tasks> tasks;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_task);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        lvTasks = findViewById(R.id.lvTasks);
        tasks = new ArrayList<>();

        ImageButton profileBtn8 = findViewById(R.id.profileButton8);
        profileBtn8.setOnClickListener(view -> {
            Intent ProfileActivityIntent = new Intent(
                    TrainerTaskActivity.this,
                    TrainerProfileActivity.class
                );
            startActivity(ProfileActivityIntent);
        });

        ImageButton moduleBtn8 = findViewById(R.id.moduleButton8);
        moduleBtn8.setOnClickListener(view -> {
            Intent ModuleActivityIntent = new Intent(
                    TrainerTaskActivity.this,
                    TrainerModuleActivity.class
            );
            startActivity(ModuleActivityIntent);
        });

        ImageButton taskBtn8 = findViewById(R.id.taskButton8);
        taskBtn8.setOnClickListener(view -> {
            Intent TaskActivityIntent = new Intent(
                    TrainerTaskActivity.this,
                    TrainerTaskActivity.class
            );
            startActivity(TaskActivityIntent);
        });

        ImageButton gradeBtn8 = findViewById(R.id.gradeButton8);
        gradeBtn8.setOnClickListener(view -> {
            Intent GradeActivityIntent = new Intent(
                    TrainerTaskActivity.this,
                    TrainerGradeActivity.class
            );
            startActivity(GradeActivityIntent);
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Creating Task");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create task:");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            progress.show();
            m_Text = input.getText().toString();

            if (!m_Text.isEmpty()) {
                OkHttpClient client = new OkHttpClient();
                String json = "{"
                    + "\"user_id\" : \"" + user_id + "\","
                    + "\"name\" : \"" + m_Text + "\""
                + "}";
                Log.d("json", json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .url("http://" + host + "/api/taskname")
                    .post(body)
                    .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        progress.dismiss();
                        Log.e("Fail", e.getMessage());
                    }

                    @Override
                    public void onResponse(
                            @NonNull Call call,
                            @NonNull Response response
                    ) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            JSONObject jo = new JSONObject(responseBody.string());
                            progress.dismiss();
                            if (!response.isSuccessful()) {
                                runOnUiThread(() -> {
                                    try {
                                        Toast.makeText(
                                            TrainerTaskActivity.this,
                                            jo.getString("error"),
                                            Toast.LENGTH_LONG
                                        ).show();
                                    } catch (JSONException e) {
                                        Log.e("Ex", e.getMessage());
                                    }
                                });
                            } else {
                                // Save data to phone
                                JSONObject data = new JSONObject(jo.getString("data"));
                                runOnUiThread(() -> {
                                    Toast.makeText(
                                        TrainerTaskActivity.this,
                                        "Task addedd successfully.",
                                        Toast.LENGTH_LONG
                                    ).show();
                                });
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        } catch (Exception e) {
                            Log.e("Ex", e.getMessage());
                        }
                    }
                });
            } else {
                Toast.makeText(this,
                    "Paki lagyan lahat ng impormasyon.",
                    Toast.LENGTH_LONG
                ).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) ->
            dialog.cancel()
        );

        ImageButton insertTaskBtn = findViewById(R.id.insertTaskBtn);
        insertTaskBtn.setOnClickListener(view -> {
            builder.show();
        });

        this.fetchTasks();
    }

    private void fetchTasks() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/taskname/" + user_id)
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        TrainerTaskActivity.this,
                        "An error has occured. Please try again.",
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
                                        TrainerTaskActivity.this,
                                        joProducts.getString("error"),
                                        Toast.LENGTH_LONG
                                    ).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            JSONArray dataArr = joProducts.getJSONArray("data");
                            Log.i("Modules", dataArr.toString());

                            tasks.clear();
                            for (int i = 0; i < dataArr.length(); i++) {
                                tasks.add(new Tasks(
                                    dataArr.getJSONObject(i).getInt("id"),
                                    dataArr.getJSONObject(i).getInt("user_id"),
                                    dataArr.getJSONObject(i).getString("name"),
                                    false,
                                    null,
                                    dataArr.getJSONObject(i).getString("created_at"),
                                    dataArr.getJSONObject(i).getString("updated_at")
                                ));
                            }
                            taskItemAdapter = new TaskItemAdapter(
                                TrainerTaskActivity.this,
                                R.layout.fragment_task,
                                tasks
                            );
                            runOnUiThread(() -> lvTasks.setAdapter(taskItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            TrainerTaskActivity.this,
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
                TrainerTaskActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}