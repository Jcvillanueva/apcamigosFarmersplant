package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmersplantlearningmanagementsystem.models.Tasks;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.MediaType;

public class FarmerTaskItemAdapter extends ArrayAdapter<Tasks> {

    public static final String host = "http://192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Context mContext;
    private final int mResource;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public FarmerTaskItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Tasks> objects) {
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

        TextView txtvFarmerTaskName = convertView.findViewById(R.id.txtvFarmerTaskName);
        TextView txtvFarmerTaskCreatedAt = convertView.findViewById(R.id.txtvFarmerTaskCreatedAt);
        TextView txtvSubmittedAt = convertView.findViewById(R.id.txtvSubmittedAt);
        ImageButton btnUploadTask = convertView.findViewById(R.id.btnUploadTask);

        txtvFarmerTaskName.setText(getItem(position).getName());
        txtvFarmerTaskCreatedAt.setText("Created at: " + getItem(position).getCreated_at());

        if (getItem(position).getIs_submitted()) {
            btnUploadTask.setImageResource(R.drawable.check_mark);
            txtvSubmittedAt.setVisibility(View.VISIBLE);

            txtvSubmittedAt.setText("Submitted at: " + getItem(position).getSubmitted_at());
        } else {
            btnUploadTask.setVisibility(View.VISIBLE);
            txtvSubmittedAt.setVisibility(View.INVISIBLE);

            btnUploadTask.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, FarmerUploadTaskImageActivity.class);
                intent.putExtra("task_id", getItem(position).getId());
                mContext.startActivity(intent);
            });
        }



        return convertView;
    }
}
