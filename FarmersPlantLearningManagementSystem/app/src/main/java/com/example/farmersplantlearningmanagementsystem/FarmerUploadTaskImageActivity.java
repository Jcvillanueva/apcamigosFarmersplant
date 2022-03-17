package com.example.farmersplantlearningmanagementsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.TaskUploads;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FarmerUploadTaskImageActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    FarmerTaskUploadItemAdapter farmerTaskUploadItemAdapter;
    ListView lvTaskPhotos;
    ArrayList<TaskUploads> taskUploads;
    Integer task_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_upload_task_image);

        Intent intent = getIntent();
        task_id = intent.getIntExtra("task_id", 0);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        Button btnUploadPhoto = findViewById(R.id.btnUploadPhoto);
        lvTaskPhotos = findViewById(R.id.lvTaskPhotos);
        taskUploads = new ArrayList<>();

        Log.d("task_id", task_id.toString());

        progress = new ProgressDialog(this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        btnUploadPhoto.setOnClickListener(view -> {
            Intent filePickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            filePickerIntent.setType("image/*");
            startActivityForResult(
                Intent.createChooser(filePickerIntent, "Select image"),
                1
            );
        });

        this.fetchFarmerTaskImages();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFile = data.getData();
                String filePath = UriUtils.getPathFromUri(this, selectedFile);;
                Log.i("File", filePath);

                String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
                Log.i("Name", filename);

                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                Log.i("Ext", file_extn);

                progress.show();

                File file = new File(filePath);

                OkHttpClient client = new OkHttpClient();
                RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        file.getName(),
                        RequestBody.create(
                            MediaType.parse("image/jpg"),
                            file
                        )
                    )
                    .addFormDataPart("user_id", user_id)
                    .addFormDataPart("task_id", String.valueOf(task_id))
                    .build();
                Request request = new Request.Builder()
                    .header("Accept", "*/*")
                    .header("Content-Type", "multipart/form-data")
                    .url("http://" + host + "/api/task")
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
                        progress.dismiss();
                        try (ResponseBody responseBody = response.body()) {
                            JSONObject jo = new JSONObject(responseBody.string());
                            if (!response.isSuccessful()) {
                                runOnUiThread(() -> {
                                    try {
                                        Toast.makeText(
                                            FarmerUploadTaskImageActivity.this,
                                            jo.getString("error"),
                                            Toast.LENGTH_LONG
                                        ).show();
                                    } catch (JSONException e) {
                                        Log.e("!Res", e.getMessage());
                                    }
                                });
                            } else {
                                runOnUiThread(() -> {
                                    Toast.makeText(
                                        FarmerUploadTaskImageActivity.this,
                                        "File uploaded successfully",
                                        Toast.LENGTH_LONG
                                    ).show();
                                });
                                Log.d("Res", jo.getString("data"));
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        } catch (Exception e) {
                            Log.e("Res", e.getMessage());
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FarmerTaskActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchFarmerTaskImages() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .url("http://" + host + "/api/task/images/" + user_id + "/" + task_id)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        FarmerUploadTaskImageActivity.this,
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
                                        FarmerUploadTaskImageActivity.this,
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
                                FarmerUploadTaskImageActivity.this,
                                R.layout.fragment_farmer_image_task,
                                taskUploads
                            );
                            runOnUiThread(() -> lvTaskPhotos.setAdapter(farmerTaskUploadItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            FarmerUploadTaskImageActivity.this,
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
                FarmerUploadTaskImageActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}