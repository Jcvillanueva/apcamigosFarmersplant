package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class TrainerLoginActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    public static final String FULLNAME = "fullname";
    public static final String PLACE_ENROLLED = "placeEnrolled";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    EditText inputEmail2;
    EditText inputPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_login);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );

        if(
            !shared.getString(USERNAME, "").isEmpty() &&
            !shared.getString(EMAIL, "").isEmpty() &&
            !shared.getString(PASSWORD, "").isEmpty()
        ) {
            Intent trainerHomepageIntent = new Intent(
                TrainerLoginActivity.this,
                TrainerHomepageActivity.class
            );
            startActivity(trainerHomepageIntent);
            finish();
        }

        this.inputEmail2 = findViewById(R.id.inputEmail2);
        this.inputPassword2 = findViewById(R.id.inputPassword2);

        ImageButton createBtn = findViewById(R.id.createAccBtn);
        createBtn.setOnClickListener(view -> {
            Intent TrainerCreateActivityIntent = new Intent(TrainerLoginActivity.this, TrainerCreateAccount.class);
            startActivity(TrainerCreateActivityIntent);
        });

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        ImageButton signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(view -> {
            progress.show();
            String inputEmail2 = this.inputEmail2.getText().toString();
            String inputPassword2 = this.inputPassword2.getText().toString();

            if (
                !inputEmail2.isEmpty() &&
                !inputPassword2.isEmpty()
            ) {
                OkHttpClient client = new OkHttpClient();
                String json = "{"
                        + "\"email\" : \"" + inputEmail2 + "\","
                        + "\"password\" : \"" + inputPassword2 + "\""
                        + "}";
                Log.d("json", json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .url("http://" + host + "/api/login")
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
                                            TrainerLoginActivity.this,
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
                                editor.putString(FULLNAME, data.getString("full_name"));
                                editor.putString(PLACE_ENROLLED, data.getString("place_enrolled"));
                                editor.putString(USERNAME, data.getString("username"));
                                editor.putString(EMAIL, data.getString("email"));
                                editor.putString(PASSWORD, data.getString("password"));
                                editor.apply();
                                Intent trainerHomepageIntent = new Intent(
                                        TrainerLoginActivity.this,
                                        TrainerHomepageActivity.class
                                );
                                startActivity(trainerHomepageIntent);
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