package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmersplantlearningmanagementsystem.models.TaskUploads;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.MediaType;

public class FarmerTaskUploadItemAdapter extends ArrayAdapter<TaskUploads> {

    public static final String host = "http://192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Context mContext;
    private final int mResource;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public FarmerTaskUploadItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskUploads> objects) {
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

        ImageView imgvTask = convertView.findViewById(R.id.imgvTask);
        Picasso.get().load(host + "/storage/" + getItem(position).getTask_path()).into(imgvTask);

        return convertView;
    }
}
