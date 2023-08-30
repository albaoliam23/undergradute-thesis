package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pikmi85.thesisfinal.Assessment;
import com.example.pikmi85.thesisfinal.Assessment2;
import com.example.pikmi85.thesisfinal.ItemAnalysis;
import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ChapterQuiz extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref;
    String s, curr_subject,curr_lesson,curr_topic, grpCode, studkey, teacherkey, timelimit, finalstatus;
    LinearLayout quizlay;
    TextView questions, timer;
    RadioButton rdbtn;
    int scored, numOfItems, index = 0, pointer = 1, chapterscore, tomillis, counter;
    Double totalscore = 0.0;
    Object q_randomize, q_type, question, canswer;
    List<String> questions_key;
    List<String> choices;
    List<String> missed;
    String[] youranswers;
    String[] correctans;
    RadioGroup[] rdgroup = new RadioGroup[100];
    long startTime, endTime, spent;
    CountDownTimer timer2;
    DecimalFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(ChapterQuiz.this, LoginScreen.class));
            finish();
        }
        setContentView(R.layout.activity_chapter_quiz);
        format = new DecimalFormat("0.0");
        scored = globalVariables.getscore();
        curr_subject = globalVariables.getcurr_subject();
        curr_lesson = globalVariables.getcurr_lesson();
        curr_topic = globalVariables.getcurr_topic();
        grpCode = globalVariables.getgrpCode();
        studkey = globalVariables.getstudentKey();
        teacherkey = globalVariables.getteacherkey();
        Bundle extras = getIntent().getExtras();
        s = extras.getString("lesson");
        quizlay = (LinearLayout) findViewById(R.id.quiz_layout);
        Button submit = (Button) findViewById(R.id.btn_submit);
        timer = (TextView) findViewById(R.id.timer);
        dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(s);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                String numOfItems1 =  newPost.get("quiz_numItems").toString();
                numOfItems = Integer.parseInt(numOfItems1);
                chapterscore = numOfItems;
                q_randomize = newPost.get("quiz_randomize");
                timelimit = newPost.get("quiz_timelimit").toString();
                counter = Integer.parseInt(timelimit);
                tomillis = (counter * 1000) + 2000;
                timer2 = new CountDownTimer(tomillis,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer.setText(String.valueOf(counter));
                        counter--;
                    }

                    @Override
                    public void onFinish() {
                        timer2.cancel();
                        int size = youranswers.length;
                        Log.e("Check", "" + questions_key.size());
                        Log.e("Check", "" + size);
                        for(int h = 0 ; h<=questions_key.size()-1 ; h++){
                            if(Objects.equals(youranswers[h], null)){
                                youranswers[h] = "Walang Sagot";
                            }
                        }
                        for(int l = 0 ; l<=questions_key.size()-1 ; l++){
                            if(!Objects.equals(youranswers[l], correctans[l])){
                                chapterscore--;
                                missed.add(questions_key.get(l));
                            }
                        }
                        onPause();
                        double scoretalga = ((double)chapterscore/(double)numOfItems);
                        totalscore = totalscore + ((scoretalga * 50) + 50);
                        for(int x = 0 ; x<=missed.size()-1 ; x++){
                            dref = FirebaseDatabase.getInstance().getReference();
                            updateitemanalysis(curr_subject,curr_lesson,curr_topic,grpCode,missed.get(x), studkey, teacherkey);
                        }
                        dref = FirebaseDatabase.getInstance().getReference();

                        long timeonQuiz = globalVariables.getTimespentonQuiz();
                        String totaltimeonQuiz = timeonQuiz + "";
                        createAssessment(grpCode, format.format(totalscore), curr_topic, teacherkey, totaltimeonQuiz, studkey, "unfinished");
                    }
                }.start();

                questions_key = new ArrayList<String>();
                missed = new ArrayList<String>();
                dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(s).child("questions");
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                questions_key.add(d.getKey());
                            }
                            for(int m = 1; m<=numOfItems; m++) {
                                rdgroup[m] = new RadioGroup(ChapterQuiz.this);
                            }
                            if(Objects.equals(q_randomize.toString(), "true")){
                                Collections.shuffle(questions_key,new Random(System.nanoTime()));

                                for(int re = questions_key.size() ; re<numOfItems ; re--){
                                    questions_key.remove(re);
                                }
                                correctans = new String[questions_key.size()];
                                youranswers = new String[questions_key.size()];
                                getquestions();
                            }else{
                                for(int re = questions_key.size() ; re<numOfItems ; re--){
                                    questions_key.remove(re);
                                }
                                correctans = new String[questions_key.size()];
                                youranswers = new String[questions_key.size()];
                                getquestions();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int selected = rdgroup[index+1].getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selected);
                youranswers[index] = radioButton.getText().toString();
                Log.e("Check",correctans[index]);
                Log.e("Check",youranswers[index]);
                if(pointer < questions_key.size()){
                    rdgroup[index+1].setVisibility(View.INVISIBLE);
                    questions.setVisibility(View.INVISIBLE);
                    pointer++;
                    index++;
                    quizlay.removeAllViews();
                    getquestions();
                }else{
                    timer2.cancel();
                    Log.e("Check", "Nasagot na lahat");
                    for(int l = 0 ; l<=questions_key.size()-1 ; l++){
                        if(!Objects.equals(youranswers[l], correctans[l])){
                            chapterscore--;
                            missed.add(questions_key.get(l));
                        }
                    }
                        onPause();
                        double scoretalga = ((double)chapterscore/(double)numOfItems);
                        totalscore = totalscore + ((scoretalga * 50) + 50);

                        for(int x = 0 ; x<=missed.size()-1 ; x++){
                            dref = FirebaseDatabase.getInstance().getReference();
                            updateitemanalysis(curr_subject,curr_lesson,curr_topic,grpCode,missed.get(x), studkey, teacherkey);
                        }
                        dref = FirebaseDatabase.getInstance().getReference();

                        long timeonQuiz = globalVariables.getTimespentonQuiz();
                        String totaltimeonQuiz = timeonQuiz + "";
                        createAssessment(grpCode, format.format(totalscore), curr_topic, teacherkey, totaltimeonQuiz, studkey, "finished");
                }


            }
        });
    }
    private void getquestions(){
        Log.e("Check", ""+index);
        dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child("quiz").child("questions").child(questions_key.get(index));
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                q_type = newPost.get("q_type");
                if(Objects.equals(q_type.toString(), "True/False")){
                    question = newPost.get("q_question");
                    canswer = newPost.get("q_answer");
                    correctans[index] = canswer.toString();
                    questions = new TextView(ChapterQuiz.this);
                    questions.setText(question.toString());
                    quizlay.addView(questions);
                    quizlay.addView(rdgroup[index+1]);
                    RadioButton choices1 = new RadioButton(ChapterQuiz.this);
                    choices1.setText("True");
                    rdgroup[index+1].addView(choices1);
                    RadioButton choices2 = new RadioButton(ChapterQuiz.this);
                    choices2.setText("False");
                    rdgroup[index+1].addView(choices2);
                }else if(Objects.equals(q_type.toString(), "Multiple Choice")){
                    choices = new ArrayList<String>();
                    question = newPost.get("q_question");
                    canswer = newPost.get("q_answer");
                    correctans[index] = canswer.toString();
                    dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child("quiz").child("questions").child(questions_key.get(index));
                    dref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                if(Objects.equals(d.getKey(), "q_answer") || Objects.equals(d.getKey(), "q_question") || Objects.equals(d.getKey(), "q_type")){

                                }else{
                                    choices.add(d.getValue().toString());
                                }
                            }
                            questions = new TextView(ChapterQuiz.this);
                            questions.setText(question.toString());
                            quizlay.addView(questions);
                            quizlay.addView(rdgroup[index+1]);

                            for(int k = 0 ; k<=choices.size()-1 ; k++){
                                rdbtn = new RadioButton(ChapterQuiz.this);
                                rdbtn.setText(choices.get(k));
                                rdgroup[index+1].addView(rdbtn);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void updateitemanalysis(String curr_subject, String curr_lesson, String curr_topic, String grpCode, String missed_key, String studkey, String teacherkey){
        ItemAnalysis item_analysis = new ItemAnalysis(studkey, 1);
        dref.child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson)
                .child("quiz").child("item_analysis").child(grpCode).child(missed_key).child(studkey).setValue(1);
        addupdateanalysislistener(curr_subject,curr_lesson,curr_topic,grpCode,missed_key,studkey, teacherkey);
    }
    private void addupdateanalysislistener(String curr_subject, String curr_lesson, String curr_topic, String grpCode, String missed_key, String studkey, String teacherkey) {
        // User data change listener
        dref.child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson)
                .child("quiz").child("item_analysis").child(grpCode).child(missed_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Check", "Success");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Check", "Failed", error.toException());
            }
        });
    }
    private void createAssessment(String grpCode, String score, String curr_topic, String teacherkey, String timeonQuiz, String studentkey, String status) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String date_quiztaken = simpleDateFormat.format(new Date());
        Assessment2 user = new Assessment2(score, timeonQuiz, date_quiztaken);
        dref.child("assessment").child(teacherkey).child(grpCode).child(studentkey).child("quiz").setValue(user);
        addUserChangeListener(studentkey, teacherkey, grpCode, status);

    }
    private void addUserChangeListener(String studentkey, String teacherkey, String grpCode,String status) {
        // User data change listener
        finalstatus = status;
        dref.child("assessment").child(teacherkey).child(grpCode).child(studentkey).child("quiz").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Check", "Success");
                globalVariables.setscore(100);
                globalVariables.setTimespentonQuiz(0,"yes");
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChapterQuiz.this);
                builder.setTitle("Warning");
                if(Objects.equals(finalstatus, "finished")){
                    finalstatus = "You have finished this chapter test.";
                }else{
                    finalstatus = "You have consume your allotted time. Unanswered questions will be considered wrong.";
                }
                builder.setMessage(finalstatus)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(ChapterQuiz.this, SelectedCourseScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("Check", "Failed", error.toException());
            }
        });
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
        globalVariables.setTimespentonQuiz(spent,"no");

    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Sorry you can't disregard your quiz.")
                .setPositiveButton(android.R.string.yes, null).create().show();
    }
}
