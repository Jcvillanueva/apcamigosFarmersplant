package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmersplantlearningmanagementsystem.models.TaskFarmers;

import java.util.ArrayList;

public class TaskUsersItemAdapter extends ArrayAdapter<TaskFarmers> {

    private final Context mContext;
    private final int mResource;

    public TaskUsersItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaskFarmers> objects) {
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

        TextView txtvTaskFarmerName = convertView.findViewById(R.id.txtvTaskFarmerName);
        TextView txtvFarmerLocation = convertView.findViewById(R.id.txtvFarmerLocation);

        txtvTaskFarmerName.setText(getItem(position).getFull_name());
        txtvFarmerLocation.setText(getItem(position).getPlace_enrolled());

        return convertView;
    }
}
