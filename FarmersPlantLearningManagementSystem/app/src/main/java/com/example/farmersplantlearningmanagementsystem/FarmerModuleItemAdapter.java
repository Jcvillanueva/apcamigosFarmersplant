package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmersplantlearningmanagementsystem.models.Modules;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.MediaType;

public class FarmerModuleItemAdapter extends ArrayAdapter<Modules> {

    public static final String host = "http://192.168.1.2:8000";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Context mContext;
    private final int mResource;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public FarmerModuleItemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Modules> objects) {
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

        TextView txtvAdditioanlModuleName = convertView.findViewById(R.id.txtvAdditioanlModuleName);
        TextView txtvAdditionalModulesCreatedAt = convertView.findViewById(R.id.txtvAdditionalModulesCreatedAt);


        txtvAdditionalModulesCreatedAt.setText("Module Created On: " + getItem(position).getCreated_at());

        txtvAdditioanlModuleName.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WebViewPDFActivity.class);
            intent.putExtra("pdf_url", host + "/storage/" + getItem(position).getFile_path());
            mContext.startActivity(intent);
        });

        txtvAdditioanlModuleName.setText(getItem(position).getFile_name());

        return convertView;
    }
}
