package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class TrainerCreateAccount extends AppCompatActivity {

    //public static final String host = "http://192.168.1.2:8000";
    //public static final String host = "192.168.1.2:8000";
    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    public static final String FULLNAME = "fullname";
    public static final String PLACE_ENROLLED = "placeEnrolled";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // if already registered redirect to trainer homepage
        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_create_account);

        Spinner dropdown = findViewById(R.id.spinnerDrpList2);
        String[] items = new String[]{"Victoria, Tarlac",
                "Brgy. 144, Pasay City",
                "Brgy. 198, Pasay City",
                "Sitio Sapangmunti, Brgy. San Mateo, Norzagaray, Bulacan",
                "Sitio Balingkupang, Brgy. Biak na bato, San Miguel, Bulacan",
                "Sitio Tamale, Bongabon, Nueva Ecija",
                "Padre Garcia, Batangas"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);

        Button createBtn = findViewById(R.id.createBtn);
        Button rtnBtn = (Button) findViewById(R.id.rtnBtn);
        EditText inputFullname = findViewById(R.id.inputFullNameT);
        EditText inputUsername = findViewById(R.id.inputUsernameT);
        EditText inputEmail = findViewById(R.id.inputEmailT);
        EditText inputPassword = findViewById(R.id.inputPassword);
        EditText inputConfPass = findViewById(R.id.inputConfPass);
        @SuppressLint("CutPasteId")
        Spinner spinnerDrpList2 = findViewById(R.id.spinnerDrpList2);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        createBtn.setOnClickListener(view -> {
            progress.show();
            String fullname = inputFullname.getText().toString();
            String placeEnrolled = spinnerDrpList2.getSelectedItem().toString();
            String username = inputUsername.getText().toString();
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            String conf_password = inputConfPass.getText().toString();
            Boolean isSamePassword = false;
            if (password.equals(conf_password)) {
                isSamePassword = true;
            } else {
                Toast.makeText(this,
                    "Password do not match.",
                    Toast.LENGTH_LONG
                ).show();
            }

            if (!fullname.isEmpty() && !placeEnrolled.isEmpty() &&
                !username.isEmpty() && !email.isEmpty() &&
                !password.isEmpty() && !conf_password.isEmpty() &&
                isSamePassword
            ) {
                OkHttpClient client = new OkHttpClient();
                String json = "{"
                        + "\"fullname\" : \"" + fullname + "\","
                        + "\"place_enrolled\" : \"" + placeEnrolled + "\","
                        + "\"username\" : \"" + username + "\","
                        + "\"email\" : \"" + email + "\","
                        + "\"password\" : \"" + password + "\""
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
                                                TrainerCreateAccount.this,
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
                                editor.putString(USERNAME, username);
                                editor.putString(EMAIL, email);
                                editor.putString(PASSWORD, password);
                                editor.apply();
                                Intent trainerHomepageintent = new Intent(
                                        TrainerCreateAccount.this,
                                        TrainerHomepageActivity.class
                                );
                                startActivity(trainerHomepageintent);
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

        rtnBtn.setOnClickListener(view -> {
            Intent TrainerLoginActivityIntent = new Intent(
                    TrainerCreateAccount.this, TrainerLoginActivity.class);
            startActivity(TrainerLoginActivityIntent);
        });
    }
}