package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class Read_Lesson_Screen extends AppCompatActivity {
    FirebaseAuth auth;
    String s, teacherkey, curr_subject, curr_lesson, pdflink;
    Button takequiz;
    long startTime, endTime, spent;
    DatabaseReference dref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Object lesson_material, lesson_sidenotes, quiz_started;
    private FloatingActionButton fab;
    LinearLayout ll;
    LinearLayout.LayoutParams params;
    Date date1, date2;
    int totalnumberofpages = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Read_Lesson_Screen.this, LoginScreen.class));
            finish();
        }
        setContentView(R.layout.activity_read__lesson__screen);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        isInternetOn();
        Bundle extras = getIntent().getExtras();
        s = extras.getString("lesson");
        takequiz = (Button) findViewById(R.id.btn_takequiz);
        takequiz.setVisibility(View.INVISIBLE);
        teacherkey = globalVariables.getteacherkey();
        curr_subject = globalVariables.getcurr_subject();
        curr_lesson = globalVariables.getcurr_lesson();
        dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(s);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                lesson_material = newPost.get("lesson_material");
                lesson_sidenotes = newPost.get("lesson_sidenotes");
                Log.e("Check", lesson_material.toString());
                downloadFile(lesson_material.toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(s).child("quiz");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    takequiz.setVisibility(View.VISIBLE);
                    takequiz.setText("No Quiz Available");
                    takequiz.setEnabled(false);
                }else{
                    Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                    quiz_started = newPost.get("quiz_started");
                    if(Objects.equals(quiz_started.toString(), "no")){
                        takequiz.setVisibility(View.VISIBLE);
                        takequiz.setText("No Quiz Available");
                        takequiz.setEnabled(false);
                    }else{
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                        String haha = newPost.get("quiz_expiration").toString();
                        try {
                            date1 = simpleDateFormat.parse(haha);
                            date2 = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                            int checkExpired = date1.compareTo(date2);
                            if(checkExpired == -1){
                                takequiz.setVisibility(View.VISIBLE);
                                takequiz.setText("Quiz Expired");
                                takequiz.setEnabled(false);
                            }else{
                                takequiz.setText("Quiz Available");
                                takequiz.setEnabled(true);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dref = database.getReference().child("assessment").child(teacherkey).child(globalVariables.getgrpCode()).child(globalVariables.getstudentKey()).child(s);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    takequiz.setVisibility(View.VISIBLE);
                    takequiz.setText("You Have Already Taken The Quiz.");
                    takequiz.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll = new LinearLayout(Read_Lesson_Screen.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(100,50,100,50);
                AlertDialog.Builder builder = new AlertDialog.Builder(Read_Lesson_Screen.this);
                builder.setTitle("Sidenotes");
                TextView htmltext = new TextView(Read_Lesson_Screen.this);
                String sidenote = "<body>" + lesson_sidenotes.toString() + "</body>";
                htmltext.setText(Html.fromHtml(sidenote));
                ll.addView(htmltext);
                builder.setMessage(htmltext.getText());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }
    private void downloadFile(String pdfname) {
        pdflink = pdfname;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://ezbio-98be1.appspot.com");
            storageRef.child(pdflink).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Log.e("CHEKING", s);
                    FileCacher<byte[]> byteCacher = new FileCacher<>(Read_Lesson_Screen.this, s);
                    List<String> topics = new ArrayList<String>();
                    topics.add(s);
                    FileCacher<List<String>> topic = new FileCacher<>(Read_Lesson_Screen.this, "topics");
                    try{
                        byteCacher.writeCache(bytes);
                        topic.writeCache(topics);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    globalVariables.setpdf(bytes);
                    PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
                    pdfView.fromBytes(bytes)
                            .enableSwipe(true) // allows to block changing pages using swipe
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                            .password(null)
                            .scrollHandle(null)
                            .onPageChange(new OnPageChangeListener() {
                                @Override
                                public void onPageChanged(int page, int pageCount) {
                                    totalnumberofpages = pageCount;

                                }
                            })
                            .onPageScroll(new OnPageScrollListener() {
                                @Override
                                public void onPageScrolled(int page, float positionOffset) {
                                    Log.e("CHeck", " "+ totalnumberofpages + " " + positionOffset + " " + page);
                                    if(page == (totalnumberofpages-3)){
                                        takequiz.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .load();
                }
            });

    }

    public void takequiz(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Read_Lesson_Screen.this);
        builder.setTitle("Are you ready to take the quiz?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(), Quiz.class);
                Bundle extras = new Bundle();
                extras.putString("lesson",s);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis();

    }
    @Override
    protected void onPause() {
        super.onPause();
        endTime = System.currentTimeMillis();
        spent = endTime - startTime;
        spent = spent / 1000;
        globalVariables.setTimespentonRead(spent,"no");
        Log.d("Check", "" + spent + "seconds");
    }
    public void onBackPressed() {
        if(Objects.equals(globalVariables.getonquiz(), "yes")){
            new AlertDialog.Builder(this)
                    .setTitle("Information")
                    .setMessage("Sorry you can't disregard your quiz.")
                    .setPositiveButton(android.R.string.yes, null).create().show();
        }else{
            Intent intent=new Intent(Read_Lesson_Screen.this, SelectedCourseScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    public final boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
            // if connected with internet
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(Read_Lesson_Screen.this, " No internet connection", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Read_Lesson_Screen.this,OfflineScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return false;
        }
        return false;
    }
}
