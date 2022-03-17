package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.TaskUploads;

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

public class TrainerTaskFarmerImagesActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    FarmerTaskUploadItemAdapter farmerTaskUploadItemAdapter;
    ListView lvTaskFarmerImages;
    ArrayList<TaskUploads> taskUploads;
    int task_id;
    int farmer_user_id;
    String farmer_name;
    String farmer_location;
    String farmer_bdate;
    String feedback;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_task_farmer_images);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        Intent intent = getIntent();
        task_id = intent.getIntExtra("task_id", 0);
        farmer_user_id = intent.getIntExtra("farmer_user_id", 0);
        farmer_name = intent.getStringExtra("farmer_name");
        farmer_location = intent.getStringExtra("farmer_location");
        farmer_bdate = intent.getStringExtra("farmer_bdate");

        TextView txtvTaskFarmerImageName = findViewById(R.id.txtvTaskFarmerImageName);
        TextView txtvTaskFarmerImageLoc = findViewById(R.id.txtvTaskFarmerImageLoc);
        TextView txtvTaskFarmerImageBdate = findViewById(R.id.txtvTaskFarmerImageBdate);

        txtvTaskFarmerImageName.setText("Name: " + farmer_name);
        txtvTaskFarmerImageLoc.setText("Location: " + farmer_location);
        txtvTaskFarmerImageBdate.setText(
            "Birth date: " + (!farmer_bdate.equals("null") ? farmer_bdate : "N/A")
        );

        lvTaskFarmerImages = findViewById(R.id.lvTaskFarmerImages);
        taskUploads = new ArrayList<>();

        progress = new ProgressDialog(this);
        progress.setTitle("Fetching farmers");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        if (task_id > 0 && farmer_user_id > 0) {
            this.fetchFarmerTaskImages();
        } else {
            Toast.makeText(
                TrainerTaskFarmerImagesActivity.this,
                "An error has occured. Task and farmer_user_id no value.",
                Toast.LENGTH_LONG
            ).show();
        }

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    OkHttpClient client = new OkHttpClient();

                    String json = "{"
                        + "\"user_id\" : \"" + farmer_user_id + "\","
                        + "\"task_id\" : \"" + task_id + "\","
                        + "\"feedback\" : \"" + feedback + "\""
                    + "}";
                    Log.d("json", json);
                    Log.d("API", host + "/api/feedback");
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .url("http://" + host + "/api/feedback")
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
                                    Handler mainHandler = new Handler(
                                        TrainerTaskFarmerImagesActivity.this.getMainLooper()
                                    );
                                    Runnable myRunnable = () -> {
                                        try {
                                            Toast.makeText(
                                                TrainerTaskFarmerImagesActivity.this,
                                                jo.getString("error"),
                                                Toast.LENGTH_LONG
                                            ).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    };
                                    mainHandler.post(myRunnable);
                                } else {
                                    Handler mainHandler = new Handler(
                                        TrainerTaskFarmerImagesActivity.this.getMainLooper()
                                    );
                                    Runnable myRunnable = () -> {
                                        Toast.makeText(
                                            TrainerTaskFarmerImagesActivity.this,
                                            "Submitted successfully.",
                                            Toast.LENGTH_LONG
                                        ).show();
                                    };
                                    mainHandler.post(myRunnable);
                                }
                            } catch (Exception e) {
                                Log.e("Ex", e.getMessage());
                            }
                        }
                    });
                    this.finish();
                    Intent TaskActivityIntent = new Intent(this, TrainerTaskActivity.class);
                    startActivity(TaskActivityIntent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText etxtmFeedback = findViewById(R.id.etxtmFeedback);

        Button btnGradeSubmit = findViewById(R.id.btnGradeSubmit);
        btnGradeSubmit.setOnClickListener(view -> {
            feedback = etxtmFeedback.getText().toString();
            if (!feedback.isEmpty()) {
                builder
                    .setMessage("Are you sure to submit feedback?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .show();

            } else {
                Toast.makeText(
                    this,
                    "Please input feedback.",
                    Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void fetchFarmerTaskImages() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/task/images/" + farmer_user_id + "/" + task_id)
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        TrainerTaskFarmerImagesActivity.this,
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
                                        TrainerTaskFarmerImagesActivity.this,
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

                            taskUploads.clear();
                            for (int i = 0; i < dataArr.length(); i++) {
                                taskUploads.add(new TaskUploads(
                                    dataArr.getJSONObject(i).getInt("id"),
                                    dataArr.getJSONObject(i).getInt("user_id"),
                                    dataArr.getJSONObject(i).getInt("task_id"),
                                    dataArr.getJSONObject(i).getString("task_path"),
                                    dataArr.getJSONObject(i).getString("task_name"),
                                    dataArr.getJSONObject(i).getString("created_at"),
                                    dataArr.getJSONObject(i).getString("updated_at")
                                ));
                            }
                            farmerTaskUploadItemAdapter = new FarmerTaskUploadItemAdapter(
                                TrainerTaskFarmerImagesActivity.this,
                                R.layout.fragment_farmer_image_task,
                                taskUploads
                            );
                            runOnUiThread(() -> {
                                lvTaskFarmerImages.setAdapter(farmerTaskUploadItemAdapter);
                                // adjust listview height based on number of images
                                ListAdapter listadp = lvTaskFarmerImages.getAdapter();
                                if (listadp != null) {
                                    int totalHeight = 0;
                                    for (int i = 0; i < listadp.getCount(); i++) {
                                        totalHeight += 600;
                                    }
                                    ViewGroup.LayoutParams params = lvTaskFarmerImages.getLayoutParams();
                                    params.height = totalHeight + (lvTaskFarmerImages.getDividerHeight() * (listadp.getCount() - 1));
                                    lvTaskFarmerImages.setLayoutParams(params);
                                    lvTaskFarmerImages.requestLayout();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            TrainerTaskFarmerImagesActivity.this,
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
                TrainerTaskFarmerImagesActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}