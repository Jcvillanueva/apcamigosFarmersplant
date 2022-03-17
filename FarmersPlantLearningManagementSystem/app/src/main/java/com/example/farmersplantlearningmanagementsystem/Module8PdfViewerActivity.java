package com.example.farmersplantlearningmanagementsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class Module8PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module8_pdf_viewer);

        PDFView module8 = findViewById(R.id.module8pdfviewer);
        module8.fromAsset("session8.pdf").load();

    }
}