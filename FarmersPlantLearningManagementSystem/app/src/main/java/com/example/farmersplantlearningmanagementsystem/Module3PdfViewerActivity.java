package com.example.farmersplantlearningmanagementsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class Module3PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module3_pdf_viewer);

        PDFView module3 = findViewById(R.id.module3pdfviewer);
        module3.fromAsset("session3.pdf").load();
    }
}