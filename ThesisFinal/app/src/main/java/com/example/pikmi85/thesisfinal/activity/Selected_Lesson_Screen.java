package com.example.pikmi85.thesisfinal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.pikmi85.thesisfinal.FirebaseImageLoader;
import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class Selected_Lesson_Screen extends AppCompatActivity {
    Button takequiz;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref = database.getReference().child("lessons");
    FirebaseAuth auth;
    ListView listview;
    ArrayList<Object> list = new ArrayList<>();
    ArrayAdapter<Object> adapter;
    String s, chapter;
    LinearLayout l;
    Object lesson_chapter, lesson_name, lesson_bullet, lesson_curriculum;
    int arraylistsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Selected_Lesson_Screen.this, LoginScreen.class));
            finish();
        }
        setContentView(R.layout.activity_selected__lesson__screen);

        listview = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_dropdown_item_1line, list);
        listview.setAdapter(adapter);
        arraylistsize = globalVariables.getchapterscount();

        Bundle extras = getIntent().getExtras();
        s = extras.getString("EXTRA_PICKEDLESSON");
        for(int x = 0 ; x<arraylistsize ; x++) {
            chapter = globalVariables.getchapters(x);
            if (Objects.equals(s, chapter)) {
                globalVariables.setIndexess(x);
                dref.addChildEventListener(new ChildEventListener() {
                    // Retrieve new posts as they are added to Firebase
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                        Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                        lesson_chapter = newPost.get("lesson_chapter");
                        if (Objects.equals(lesson_chapter, chapter)) {
                            lesson_name = newPost.get("lesson_name");
                            lesson_bullet = newPost.get("lesson_bullet");
                            lesson_curriculum = newPost.get("lesson_curriculum");
                            list.add(lesson_name);
                            globalVariables.setlessons(lesson_name.toString());
                            globalVariables.setcurriculumBullet(lesson_curriculum.toString(),lesson_bullet.toString());
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                    //... ChildEventListener also defines onChildChanged, onChildRemoved,
                    //    onChildMoved and onCanceled, covered in later sections.
                });

                break;
            }

        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pickedLessonName = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(getBaseContext(), Read_Lesson_Screen.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_PICKEDLESSONNAME",pickedLessonName);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }
}








