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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.Modules;

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

public class TrainerModuleActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    ModuleItemAdapter moduleItemAdapter;
    ListView lvModules;
    ArrayList<Modules> modules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_module);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        user_id = shared.getString(USERID, "");

        lvModules = findViewById(R.id.lvModules);
        modules = new ArrayList<>();

        Button module1T = findViewById(R.id.module1T);
        Button module2T = findViewById(R.id.module2T);
        Button module3T = findViewById(R.id.module3T);
        Button module4T = findViewById(R.id.module4T);
        Button module5T = findViewById(R.id.module5T);
        Button module6T = findViewById(R.id.module6T);
        Button module7T = findViewById(R.id.module7T);
        Button module8T = findViewById(R.id.module8T);

        module1T.setOnClickListener(view -> {
            Intent module1TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module1PdfViewerActivity.class);
            startActivity(module1TIntent);
        });

        module2T.setOnClickListener(view -> {
            Intent module2tIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module2PdfViewerActivity.class);
            startActivity(module2tIntent);
        });

        module3T.setOnClickListener(view -> {
            Intent module3TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module3PdfViewerActivity.class);
            startActivity(module3TIntent);
        });

        module4T.setOnClickListener(view -> {
            Intent module4TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module4PdfViewerActivity.class);
            startActivity(module4TIntent);
        });

        module5T.setOnClickListener(view -> {
            Intent module5TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module5PdfViewerActivity.class);
            startActivity(module5TIntent);
        });

        module6T.setOnClickListener(view -> {
            Intent module6TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module6PdfViewerActivity.class);
            startActivity(module6TIntent);
        });

        module7T.setOnClickListener(view -> {
            Intent module7TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module7PdfViewerActivity.class);
            startActivity(module7TIntent);
        });

        module8T.setOnClickListener(view -> {
            Intent module8TIntent =
                    new Intent(TrainerModuleActivity.this,
                            Module8PdfViewerActivity.class);
            startActivity(module8TIntent);
        });

        ImageButton profileBtn9 = findViewById(R.id.profileButton9);
        profileBtn9.setOnClickListener(view -> {
            Intent ProfileActivityIntent =
                    new Intent(TrainerModuleActivity.this,
                            TrainerProfileActivity.class);
            startActivity(ProfileActivityIntent);
        });

        ImageButton moduleBtn9 = findViewById(R.id.moduleButton9);
        moduleBtn9.setOnClickListener(view -> {
            Intent ModuleActivityIntent =
                    new Intent(TrainerModuleActivity.this,
                            TrainerModuleActivity.class);
            startActivity(ModuleActivityIntent);
        });

        ImageButton taskBtn9 = findViewById(R.id.taskButton9);
        taskBtn9.setOnClickListener(view -> {
            Intent TaskActivityIntent =
                    new Intent(TrainerModuleActivity.this,
                            TrainerTaskActivity.class);
            startActivity(TaskActivityIntent);
        });

        ImageButton gradeBtn9 = findViewById(R.id.gradeButton9);
        gradeBtn9.setOnClickListener(view -> {
            Intent GradeActivityIntent =
                    new Intent(TrainerModuleActivity.this,
                    TrainerGradeActivity.class);
            startActivity(GradeActivityIntent);
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        ImageButton insertModuleBtn = findViewById(R.id.insertModuleBtn);
        insertModuleBtn.setOnClickListener(view -> {
            Intent filePickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
            filePickerIntent.setType("application/pdf");
            startActivityForResult(
                Intent.createChooser(filePickerIntent, "Select PDF File"),
                1
            );
        });

        this.fetchModules();
    }

    @Override
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
                                MediaType.parse("application/pdf"),
                                file
                            )
                        )
                        .addFormDataPart("user_id", user_id)
                        .build();
                Request request = new Request.Builder()
                        .header("Accept", "*/*")
                        .header("Content-Type", "multipart/form-data")
                        .url("http://" + host + "/api/pdf")
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
                                            TrainerModuleActivity.this,
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
                                        TrainerModuleActivity.this,
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

    private void fetchModules() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/pdf/" + user_id)
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        TrainerModuleActivity.this,
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
                                            TrainerModuleActivity.this,
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

                            modules.clear();
                            for (int i = 0; i < dataArr.length(); i++) {
                                modules.add(new Modules(
                                    dataArr.getJSONObject(i).getInt("id"),
                                    dataArr.getJSONObject(i).getInt("user_id"),
                                    dataArr.getJSONObject(i).getString("file_path"),
                                    dataArr.getJSONObject(i).getString("file_name"),
                                    dataArr.getJSONObject(i).getString("created_at"),
                                    dataArr.getJSONObject(i).getString("updated_at")
                                ));
                            }
                            moduleItemAdapter = new ModuleItemAdapter(
                                    TrainerModuleActivity.this,
                                    R.layout.fragment_module,
                                    modules
                            );
                            runOnUiThread(() -> lvModules.setAdapter(moduleItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            TrainerModuleActivity.this,
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
                TrainerModuleActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}