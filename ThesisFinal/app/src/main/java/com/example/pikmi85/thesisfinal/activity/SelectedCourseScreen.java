package com.example.pikmi85.thesisfinal.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pikmi85.thesisfinal.Assessment;
import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.fragment.PhotosFragment;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectedCourseScreen extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    boolean isenabled = true;
    DatabaseReference dref;
    ListView listview;
    ArrayList<Object> list = new ArrayList<>();
    ArrayAdapter<Object> adapter;
    FirebaseAuth auth;
    Object about_subject2, teacherfname, teacherlname;
    int size, x;
    String teacherkey, quizstatus;
    List<String> lessons;
    Button[] btngroup;
    LinearLayout.LayoutParams params;
    LinearLayout ll;
    TextView about_subject, subj_title, subj_members, subj_teacher;
    AlertDialog.Builder builder;
    AlertDialog builder2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(SelectedCourseScreen.this, LoginScreen.class));
            finish();
        }
        isInternetOn();
        setContentView(R.layout.activity_selected_course_screen);
        teacherkey = globalVariables.getteacherkey();
        builder = new AlertDialog.Builder(SelectedCourseScreen.this);
        listview = (ListView) findViewById(R.id.listview);
        about_subject = (TextView) findViewById(R.id.txt_aboutsubj);
        subj_title = (TextView) findViewById(R.id.txt_subj_title);
        subj_members = (TextView) findViewById(R.id.txt_subj_members_count);
        subj_teacher = (TextView) findViewById(R.id.txt_subj_teacher);
        subj_title.setText(globalVariables.getcurr_subject());
        int counter = (int) globalVariables.getcurrclassmemcnt() - 1;
        subj_members.setText("You have " + counter + " classmates");
        adapter = new ArrayAdapter<Object>(this, R.layout.listview_text2, list);
        listview.setAdapter(adapter);
        dref = database.getReference().child("assessment").child(teacherkey).child(globalVariables.getgrpCode()).child(globalVariables.getstudentKey()).child("quiz");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    quizstatus = "You've already taken the quiz.";
                    isenabled = false;
                }else{
                    quizstatus = "Chapter Quiz";
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dref = database.getReference().child("members").child(teacherkey);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                teacherfname = newPost.get("firstname");
                teacherlname = newPost.get("lastname");
                subj_teacher.setText("Prof. "+teacherfname.toString() + " " + teacherlname.toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dref = database.getReference().child("subjects").child(teacherkey).child(globalVariables.getcurr_subject());
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                about_subject2 = newPost.get("subj_description");
                about_subject.setText("\"" + about_subject2 + "\"");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                if(!Objects.equals(snapshot.getKey(), "subj_date_added") && !Objects.equals(snapshot.getKey(), "subj_description")){
                    list.add(snapshot.getKey());
                    globalVariables.setchapters(snapshot.getKey());
                    adapter.notifyDataSetChanged();
                }

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
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                builder.setTitle("Select Lesson");
                ll = new LinearLayout(SelectedCourseScreen.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(100,50,100,50);
                String pickedTopic = String.valueOf(parent.getItemAtPosition(position));
                globalVariables.setcurr_lesson(pickedTopic);
                lessons = new ArrayList<String>();
                dref = database.getReference().child("subjects").child(teacherkey).child(globalVariables.getcurr_subject()).child(pickedTopic);
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                if(!Objects.equals(d.getKey(), "chap_date_added")){
                                    lessons.add(d.getKey());
                                }
                            }
                            size = lessons.size();
                            btngroup = new Button[size];
                            Log.e("G", "" + size);
                            for(x = 0 ; x<=size-1 ; x++){
                                final int y = x;
                                btngroup[x] = new Button(SelectedCourseScreen.this);
                                if(Objects.equals(lessons.get(x), "quiz")){
                                    btngroup[x].setText(quizstatus);
                                    if(!isenabled){
                                        btngroup[x].setEnabled(isenabled);
                                    }else{
                                        btngroup[x].setEnabled(isenabled);
                                    }
                                }else{
                                    btngroup[x].setText(lessons.get(x));
                                }
                                btngroup[x].setBackgroundColor(btngroup[x].getContext().getResources().getColor(R.color.darkorange));
                                btngroup[x].setLayoutParams(params);
                                btngroup[x].setId(x);
                                Log.e("Checks", "" + btngroup[x].getId());
                                btngroup[y].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (btngroup[y].getId() == ((Button) v).getId()){
                                            int id = btngroup[y].getId();
                                            if(btngroup[id].getText() == "Chapter Quiz"){
                                                Intent intent=new Intent(SelectedCourseScreen.this, ChapterQuiz.class);
                                                intent.putExtra("lesson", lessons.get(id));
                                                globalVariables.setcurr_topic(lessons.get(id));
                                                builder2.dismiss();
                                                startActivity(intent);
                                            }else{
                                                Intent intent=new Intent(SelectedCourseScreen.this, Read_Lesson_Screen.class);
                                                intent.putExtra("lesson", lessons.get(id));
                                                globalVariables.setcurr_topic(lessons.get(id));
                                                builder2.dismiss();
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                });
                                ll.addView(btngroup[x]);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                builder.setView(ll);
                builder2 = builder.show();
            }
        });


    }
    @Override
    public void onStop(){
        super.onStop();
        if(builder2 != null){
            builder2.dismiss();
        }
        this.recreate();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SelectedCourseScreen.this, MainScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

            Toast.makeText(SelectedCourseScreen.this, " No internet connection", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SelectedCourseScreen.this,OfflineScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return false;
        }
        return false;
    }
}


