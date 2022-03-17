package com.example.farmersplantlearningmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farmersplantlearningmanagementsystem.models.Modules;

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

public class FarmerModuleActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    String user_id;
    ProgressDialog progress;
    FarmerModuleItemAdapter farmerModuleItemAdapter;
    ListView lvAdditionalModules;
    ArrayList<Modules> modules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_module);

        lvAdditionalModules = findViewById(R.id.lvAdditionalModules);
        modules = new ArrayList<>();

        Button module1F = findViewById(R.id.module1F);
        Button module2F = findViewById(R.id.module2F);
        Button module3F = findViewById(R.id.module3F);
        Button module4F = findViewById(R.id.module4F);
        Button module5F = findViewById(R.id.module5F);
        Button module6F = findViewById(R.id.module6F);
        Button module7F = findViewById(R.id.module7F);
        Button module8F = findViewById(R.id.module8F);

        module1F.setOnClickListener(view -> {
            Intent module1FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module1PdfViewerActivity.class);
            startActivity(module1FIntent);
        });

        module2F.setOnClickListener(view -> {
            Intent module2FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module2PdfViewerActivity.class);
            startActivity(module2FIntent);
        });

        module3F.setOnClickListener(view -> {
            Intent module3FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module3PdfViewerActivity.class);
            startActivity(module3FIntent);
        });

        module4F.setOnClickListener(view -> {
            Intent module4FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module4PdfViewerActivity.class);
            startActivity(module4FIntent);
        });

        module5F.setOnClickListener(view -> {
            Intent module5FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module5PdfViewerActivity.class);
            startActivity(module5FIntent);
        });

        module6F.setOnClickListener(view -> {
            Intent module6FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module6PdfViewerActivity.class);
            startActivity(module6FIntent);
        });

        module7F.setOnClickListener(view -> {
            Intent module7FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module7PdfViewerActivity.class);
            startActivity(module7FIntent);
        });

        module8F.setOnClickListener(view -> {
            Intent module8FIntent =
                    new Intent(FarmerModuleActivity.this,
                            Module8PdfViewerActivity.class);
            startActivity(module8FIntent);
        });

        ImageButton profileBtn4 = (ImageButton) findViewById(R.id.profileButton4);
        profileBtn4.setOnClickListener(view -> {
            Intent FarmerProfileActivityIntent =
                    new Intent(FarmerModuleActivity.this,
                            FarmerProfileActivity.class);
            startActivity(FarmerProfileActivityIntent);
        });

        ImageButton moduleBtn4 = findViewById(R.id.moduleButton4);
        moduleBtn4.setOnClickListener(view -> {
            Intent FarmerModuleActivityIntent =
                    new Intent(FarmerModuleActivity.this,
                            FarmerModuleActivity.class);
            startActivity(FarmerModuleActivityIntent);
        });

        ImageButton taskBtn4 = findViewById(R.id.taskButton4);
        taskBtn4.setOnClickListener(view -> {
            Intent FarmerTaskActivityIntent =
                    new Intent(FarmerModuleActivity.this,
                            FarmerTaskActivity.class);
            startActivity(FarmerTaskActivityIntent);
        });

        ImageButton gradeBtn4 = findViewById(R.id.gradeButton4);
        gradeBtn4.setOnClickListener(view -> {
            Intent FarmerGradeActivityIntent =
                    new Intent(FarmerModuleActivity.this,
                            FarmerGradeActivity.class);
            startActivity(FarmerGradeActivityIntent);
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Fetching");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        this.fetchModules();
    }

    private void fetchModules() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .url("http://" + host + "/api/pdf/get/all")
                .get()
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    progress.dismiss();
                    Log.e("Fail", e.getMessage());
                    Toast.makeText(
                        FarmerModuleActivity.this,
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
                                        FarmerModuleActivity.this,
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
                            farmerModuleItemAdapter = new FarmerModuleItemAdapter(
                                FarmerModuleActivity.this,
                                R.layout.fragment_additional_module,
                                modules
                            );
                            runOnUiThread(() -> lvAdditionalModules.setAdapter(farmerModuleItemAdapter));
                        }
                    } catch (JSONException e) {
                        Log.e("Ex", Log.getStackTraceString(e));
                        Toast.makeText(
                            FarmerModuleActivity.this,
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
                FarmerModuleActivity.this,
                "An error has occured. Please try again.",
                Toast.LENGTH_LONG
            ).show();
        }
    }
}