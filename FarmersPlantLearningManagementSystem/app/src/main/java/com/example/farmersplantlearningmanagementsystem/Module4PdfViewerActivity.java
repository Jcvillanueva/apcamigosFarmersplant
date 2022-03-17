package com.example.farmersplantlearningmanagementsystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class Module4PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module4_pdf_viewer);

        PDFView module4 = findViewById(R.id.module4pdfviewer);
        module4.fromAsset("session4.pdf").load();
    }
}