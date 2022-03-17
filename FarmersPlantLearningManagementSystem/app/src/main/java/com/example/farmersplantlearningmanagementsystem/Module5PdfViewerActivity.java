package com.example.farmersplantlearningmanagementsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class Module5PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module5_pdf_viewer);

        PDFView module5 = findViewById(R.id.module5pdfviewer);
        module5.fromAsset("session5.pdf").load();
    }
}