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

import com.example.farmersplantlearningmanagementsystem.models.Feedbacks;
import com.example.farmersplantlearningmanagementsystem.models.Modules;

import java.util.ArrayList;

public class TrainerFeedbackItemAdapter extends ArrayAdapter<Feedbacks> {

    private final Context mContext;
    private final int mResource;

    public TrainerFeedbackItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Feedbacks> objects) {
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

        TextView txtvFeedbackTaskName = convertView.findViewById(R.id.txtvFeedbackTaskName);
        TextView txtvCreatedAt = convertView.findViewById(R.id.txtvCreatedAt);
        TextView txtvFeedback = convertView.findViewById(R.id.txtvFeedback);

        txtvFeedbackTaskName.setText("Task: " + getItem(position).getTask());
        txtvCreatedAt.setText("Task Graded On: " + getItem(position).getCreated_at());
        txtvFeedback.setText("Feedback: " + getItem(position).getFeedback());

        return convertView;
    }
}
