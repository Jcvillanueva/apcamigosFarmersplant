package com.example.farmersplantlearningmanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewPDFActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_web_view_pdfactivity);

        Intent intent = getIntent();
        String pdfUrl = intent.getStringExtra("pdf_url");

        Log.d("pdfUrl", pdfUrl);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
        startActivity(browserIntent);
    }
}