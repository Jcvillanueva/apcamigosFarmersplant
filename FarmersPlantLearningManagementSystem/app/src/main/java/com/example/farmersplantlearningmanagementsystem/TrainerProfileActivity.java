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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TrainerProfileActivity extends AppCompatActivity {

    public static final String host = "192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SHARED_PREFS_TOKEN = "sharedPrefsToken";
    public static final String USERID = "userid";
    public static final String FULLNAME = "fullname";
    public static final String PLACE_ENROLLED = "placeEnrolled";
    public static final String HOUSE_NO = "houseNo";
    public static final String STREET_1 = "street1";
    public static final String STREET_2 = "street2";
    public static final String BARANGAY = "barangay";
    public static final String CITY = "city";
    public static final String ZIP = "zip";
    public static final String BDATEMON = "bdatemon";
    public static final String BDATEDAY = "bdateday";
    public static final String BDATEYEAR = "bdateyear";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);

        SharedPreferences shared = this.getSharedPreferences(
                SHARED_PREFS_TOKEN,
                Context.MODE_PRIVATE
        );
        String user_id = shared.getString(USERID, "");
        String fullname = shared.getString(FULLNAME, "");
        String place_enrolled = shared.getString(PLACE_ENROLLED, "");

        String houseNo = shared.getString(HOUSE_NO, "");
        String street1 = shared.getString(STREET_1, "");
        String street2 = shared.getString(STREET_2, "");
        String barangay = shared.getString(BARANGAY, "");
        String city = shared.getString(CITY, "");
        String zip = shared.getString(ZIP, "");
        String bdatemon = shared.getString(BDATEMON, "");
        String bdateday = shared.getString(BDATEDAY, "");
        String bdateyear = shared.getString(BDATEYEAR, "");

        EditText editName = findViewById(R.id.editFullName);
        EditText editHouseNo = findViewById(R.id.editHouseNo_2);
        EditText editStreet1 = findViewById(R.id.editStreet_1);
        EditText editStreet2 = findViewById(R.id.editStreet_2);
        EditText editBarangay = findViewById(R.id.editBrgy);
        EditText editCity = findViewById(R.id.editCty);
        EditText editZip = findViewById(R.id.editZp);
        Spinner spnMon = findViewById(R.id.spnMon2);
        Spinner spnDay = findViewById(R.id.spnDay2);
        Spinner spnYear = findViewById(R.id.spnYear2);

        ArrayList<String> years = new ArrayList<>();
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i > 1900; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                years
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnYear.setAdapter(adapter);

        editName.setText(fullname);
        editHouseNo.setText(houseNo);
        editStreet1.setText(street1);
        editStreet2.setText(street2);
        editBarangay.setText(barangay);
        editCity.setText(city);
        editZip.setText(zip);

        List<String> mon = Arrays.asList(getResources().getStringArray(R.array.month));
        List<String> day = Arrays.asList(getResources().getStringArray(R.array.day));

        spnMon.setSelection(mon.indexOf(bdatemon));
        spnDay.setSelection(day.indexOf(bdateday));
        spnYear.setSelection(adapter.getPosition(bdateyear));

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        ImageButton editProfileBtn = findViewById(R.id.editProfileBtn2);
        editProfileBtn.setOnClickListener(view -> {
            progress.show();
            if (
                    !editHouseNo.getText().toString().isEmpty() &&
                                !editStreet1.getText().toString().isEmpty() &&
                                !editStreet2.getText().toString().isEmpty() &&
                                !editBarangay.getText().toString().isEmpty() &&
                                !editCity.getText().toString().isEmpty() &&
                                !editStreet1.getText().toString().isEmpty() &&
                                !editZip.getText().toString().isEmpty()
            ) {
                OkHttpClient client = new OkHttpClient();
                String selectedMon = spnMon.getSelectedItem().toString();
                String formatSelectedMon;
                switch (selectedMon) {
                    case "Jan":
                        formatSelectedMon = "01";
                        break;
                    case "Feb":
                        formatSelectedMon = "02";
                        break;
                    case "Mar":
                        formatSelectedMon = "03";
                        break;
                    case "Apr":
                        formatSelectedMon = "04";
                        break;
                    case "May":
                        formatSelectedMon = "05";
                        break;
                    case "Jun":
                        formatSelectedMon = "06";
                        break;
                    case "Jul":
                        formatSelectedMon = "07";
                        break;
                    case "Aug":
                        formatSelectedMon = "08";
                        break;
                    case "Sep":
                        formatSelectedMon = "09";
                        break;
                    case "Oct":
                        formatSelectedMon = "10";
                        break;
                    case "Nov":
                        formatSelectedMon = "11";
                        break;
                    default:
                        formatSelectedMon = "12";
                        break;
                }
                String formatBdate = spnYear.getSelectedItem().toString() + "-"
                        + formatSelectedMon + "-"
                        + spnDay.getSelectedItem().toString();
                String json = "{"
                        + "\"user_id\" : \"" + user_id + "\","
                        + "\"house_no\" : \"" + editHouseNo.getText().toString() + "\","
                        + "\"street_1\" : \"" + editStreet1.getText().toString() + "\","
                        + "\"street_2\" : \"" + editStreet2.getText().toString() + "\","
                        + "\"barangay\" : \"" + editBarangay.getText().toString() + "\","
                        + "\"city\" : \"" + editCity.getText().toString() + "\","
                        + "\"zip\" : \"" + editZip.getText().toString() + "\","
                        + "\"bdate\" : \"" + formatBdate + "\""
                        + "}";
                Log.d("json", json);
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .url("http://" + host + "/api/profile")
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
                                                TrainerProfileActivity.this,
                                                jo.getString("error"),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    } catch (JSONException e) {
                                        Log.e("Ex", e.getMessage());
                                    }
                                });
                            } else {
                                SharedPreferences sharedPreferences = getSharedPreferences(
                                        SHARED_PREFS_TOKEN,
                                        MODE_PRIVATE
                                );
                                @SuppressLint("CommitPrefEdits")
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(HOUSE_NO, editHouseNo.getText().toString());
                                editor.putString(STREET_1, editStreet1.getText().toString());
                                editor.putString(STREET_2, editStreet2.getText().toString());
                                editor.putString(BARANGAY, editBarangay.getText().toString());
                                editor.putString(CITY, editCity.getText().toString());
                                editor.putString(ZIP, editZip.getText().toString());
                                editor.putString(BDATEMON, spnMon.getSelectedItem().toString());
                                editor.putString(BDATEDAY, spnDay.getSelectedItem().toString());
                                editor.putString(BDATEYEAR, spnYear.getSelectedItem().toString());
                                editor.apply();
                                progress.dismiss();
                                runOnUiThread(() -> {
                                    try {
                                        Toast.makeText(TrainerProfileActivity.this,
                                                "Saved.",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    } catch (Exception e) {
                                        Log.e("Ex", e.getMessage());
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Log.e("Ex", e.getMessage());
                        }
                    }
                });
            } else {
                progress.dismiss();
                Toast.makeText(this,
                        "Paki lagyan lahat ng impormasyon.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        ImageButton profileBtn7 = findViewById(R.id.profileButton7);
        profileBtn7.setOnClickListener(view -> {
            Intent ProfileActivityIntent = new Intent(TrainerProfileActivity.this, TrainerProfileActivity.class);
            startActivity(ProfileActivityIntent);
        });

        ImageButton moduleBtn7 = findViewById(R.id.moduleButton7);
        moduleBtn7.setOnClickListener(view -> {
            Intent ModuleActivityIntent = new Intent(TrainerProfileActivity.this, TrainerModuleActivity.class);
            startActivity(ModuleActivityIntent);
        });

        ImageButton taskBtn7 = findViewById(R.id.taskButton7);
        taskBtn7.setOnClickListener(view -> {
            Intent TaskActivityIntent = new Intent(TrainerProfileActivity.this, TrainerTaskActivity.class);
            startActivity(TaskActivityIntent);
        });

        ImageButton gradeBtn7 = findViewById(R.id.gradeButton7);
        gradeBtn7.setOnClickListener(view -> {
            Intent GradeActivityIntent = new Intent(TrainerProfileActivity.this, TrainerGradeActivity.class);
            startActivity(GradeActivityIntent);
        });

    }
}