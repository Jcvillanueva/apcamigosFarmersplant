package com.example.farmersplantlearningmanagementsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class Module1PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        PDFView module1 = findViewById(R.id.module1pdfviewer);
        module1.fromAsset("session1.pdf").load();
    }
}