package com.example.pikmi85.thesisfinal.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kosalgeek.android.caching.FileCacher;

import java.io.File;
import java.io.IOException;

public class ReviewLessonsScreen extends AppCompatActivity {
    FirebaseAuth auth;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_lessons_screen);
        Bundle extras = getIntent().getExtras();
        s = extras.getString("pickedTopic");
        FileCacher<byte[]> cacher = new FileCacher<>(ReviewLessonsScreen.this, s);
        try {
            byte[] bytes =  cacher.readCache();
            PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
            pdfView.fromBytes(bytes)
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
