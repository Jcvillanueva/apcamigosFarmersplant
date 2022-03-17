package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FarmerLoginActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    public static final String FULLNAME = "fullname";
    public static final String PLACE_ENROLLED = "placeEnrolled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // if already registered redirect to farmer homepage
        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        if(
                !shared.getString(FULLNAME, "").isEmpty() &&
                        !shared.getString(PLACE_ENROLLED, "").isEmpty()
        ) {
            Intent startFarmerHomepageActivity = new Intent(
                    FarmerLoginActivity.this,
                    FarmerHomepageActivity.class
            );
            startActivity(startFarmerHomepageActivity);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);

        Spinner dropdown = findViewById(R.id.spinnerDrpList);
        String[] items = new String[]{
            "Victoria, Tarlac",
            "Brgy. 144, Pasay City",
            "Brgy. 198, Pasay City",
            "Sitio Sapangmunti, Brgy. San Mateo, Norzagaray, Bulacan",
            "Sitio Balingkupang, Brgy. Biak na bato, San Miguel, Bulacan",
            "Sitio Tamale, Bongabon, Nueva Ecija",
            "Padre Garcia, Batangas"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            items
        );
        dropdown.setAdapter(adapter);

        ImageButton startBtn = findViewById(R.id.startBtn);
        EditText inputFullname = findViewById(R.id.inputFullname);
        @SuppressLint("CutPasteId")
        Spinner spinnerDrpList = findViewById(R.id.spinnerDrpList);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        startBtn.setOnClickListener(view -> {
            progress.show();
            String fullname = inputFullname.getText().toString();
            String placeEnrolled = spinnerDrpList.getSelectedItem().toString();

            if (!fullname.isEmpty() && !placeEnrolled.isEmpty()) {
                OkHttpClient client = new OkHttpClient();
                String json = "{"
                        + "\"fullname\" : \"" + fullname + "\","
                        + "\"place_enrolled\" : \"" + placeEnrolled + "\""
                        + "}";
                Log.d("json", json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .url("http://" + host + "/api/register")
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
                                            FarmerLoginActivity.this,
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
                                SharedPreferences sharedPreferences = getSharedPreferences(
                                        SHARED_PREFS_TOKEN,
                                        MODE_PRIVATE
                                );
                                @SuppressLint("CommitPrefEdits")
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(USERID, data.getString("id"));
                                editor.putString(FULLNAME, fullname);
                                editor.putString(PLACE_ENROLLED, placeEnrolled);
                                editor.apply();
                                Intent startScreenActivityIntent = new Intent(
                                        FarmerLoginActivity.this,
                                        StartScreenActivity.class
                                );
                                startActivity(startScreenActivityIntent);
                                finish();
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
    }
}