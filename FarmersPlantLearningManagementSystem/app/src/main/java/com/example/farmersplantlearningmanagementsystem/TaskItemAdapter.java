package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmersplantlearningmanagementsystem.models.Tasks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TaskItemAdapter extends ArrayAdapter<Tasks> {

    public static final String host = "http://192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Context mContext;
    private final int mResource;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public TaskItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Tasks> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView txtvTaskName = convertView.findViewById(R.id.txtvTaskName);
        TextView txtvTaskCreatedAt = convertView.findViewById(R.id.txtvTaskCreatedAt);
        Button btnDelTaskName = convertView.findViewById(R.id.btnDelTaskName);

        txtvTaskName.setText(getItem(position).getName());

        ProgressDialog progress;
        progress = new ProgressDialog(mContext);
        progress.setTitle("Deleting");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    OkHttpClient client = new OkHttpClient();
                    String json = "{"
                        + "\"id\" : \"" + getItem(position).getId() + "\""
                    + "}";
                    Log.d("json", json);
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .url(host + "/api/taskname")
                        .delete(body)
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
                                    Handler mainHandler = new Handler(mContext.getMainLooper());
                                    Runnable myRunnable = () -> {
                                        try {
                                            Toast.makeText(
                                                    mContext,
                                                    jo.getString("error"),
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    };
                                    mainHandler.post(myRunnable);
                                } else {
                                    Handler mainHandler = new Handler(mContext.getMainLooper());
                                    Runnable myRunnable = () -> {
                                        Toast.makeText(
                                                mContext,
                                                "Deleted successfully.",
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
                    ((Activity) mContext).finish();
                    ((Activity) mContext).overridePendingTransition(0, 0);
                    mContext.startActivity(((Activity) mContext).getIntent());
                    ((Activity) mContext).overridePendingTransition(0, 0);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        btnDelTaskName.setOnClickListener(view -> {
            builder
                .setMessage("Are you sure to delete task?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
        });

        txtvTaskCreatedAt.setText("Task Created On: " + getItem(position).getCreated_at());

        txtvTaskName.setOnClickListener(view -> {
            Intent intent = new Intent(
                ((Activity) mContext),
                TrainerTaskFarmersActivity.class
            );
            intent.putExtra("task_id", getItem(position).getId());
            mContext.startActivity(intent);
        });

        return convertView;
    }
}
