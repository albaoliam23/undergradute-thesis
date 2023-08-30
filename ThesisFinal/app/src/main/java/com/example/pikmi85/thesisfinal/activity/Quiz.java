package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;
import com.example.pikmi85.thesisfinal.Assessment;
import com.example.pikmi85.thesisfinal.ItemAnalysis;
import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Quiz extends AppCompatActivity {
    String s, curr_subject,curr_lesson,curr_topic, findings, grpCode, studkey, teacherkey, timelimit;
    Object question, canswer, q_type, q_randomize;
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref;
    Double totalscore = 0.0;
    int scored, numOfItems;
    RadioGroup[] rdgroup = new RadioGroup[100];
    RadioButton rdbtn;
    TextView questions, timer;
    String[] youranswers;
    String[] correctans;
    List<String> missed;
    long startTime, endTime, spent;
    List<String> questions_key;
    List<String> choices;
    int index = 0;
    int pointer = 1;
    LinearLayout quizlay;
    int tomillis;
    public int counter;
    AlertDialog ad;
    CountDownTimer timer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Quiz.this, LoginScreen.class));
            finish();
        }
        setContentView(R.layout.activity_quiz);
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
        dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(curr_topic).child("quiz");
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                String numOfItems1 =  newPost.get("quiz_numItems").toString();
                q_randomize = newPost.get("quiz_randomize");
                timelimit = newPost.get("quiz_timelimit").toString();
                counter = Integer.parseInt(timelimit);
                tomillis = (counter * 1000) + 2000;
                timer2 = new CountDownTimer(tomillis,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer.setText(String.format("%02d:%02d:%02d", counter / 60, counter % 60, 0));
                        counter--;
                    }
                    @Override
                    public void onFinish() {
                        repeatquiz("timeexpired");
                        timer2.cancel();
                    }
                }.start();
                numOfItems = Integer.parseInt(numOfItems1);
                questions_key = new ArrayList<String>();
                missed = new ArrayList<String>();
                dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(curr_topic).child("quiz").child("questions");
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                questions_key.add(d.getKey());
                            }
                            for(int m = 1; m<=numOfItems; m++) {
                                rdgroup[m] = new RadioGroup(Quiz.this);
                            }
                            if(Objects.equals(q_randomize.toString(), "yes")){
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
                if(selected == -1){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Quiz.this);
                    builder.setTitle("Information");
                    builder.setMessage("Please choose your answer.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ad.dismiss();
                                }
                            });
                    ad = builder.show();
                }else{
                    RadioButton radioButton = (RadioButton) findViewById(selected);
                    youranswers[index] = radioButton.getText().toString();
                    if(pointer < questions_key.size()){
                        rdgroup[index+1].setVisibility(View.INVISIBLE);
                        questions.setVisibility(View.INVISIBLE);
                        pointer++;
                        index++;
                        quizlay.removeAllViews();
                        getquestions();
                    }else{
                        timer2.cancel();
                        for(int l = 0 ; l<=questions_key.size()-1 ; l++){
                            if(!Objects.equals(youranswers[l], correctans[l])){
                                findings = "fail";
                                missed.add(questions_key.get(l));
                            }

                        }
                        if(Objects.equals(findings, "fail")){
                            for(int y = 0 ; y<=missed.size()-1 ; y++){
                                dref = FirebaseDatabase.getInstance().getReference();
                                updateitemanalysis(curr_subject,curr_lesson,curr_topic,grpCode,missed.get(y), studkey, teacherkey);
                            }
                            repeatquiz("wronganswer");
                        }else{
                            onPause();
                            totalscore = totalscore + scored;
                            String curr_topic = globalVariables.getcurr_topic();
                            String grpCode = globalVariables.getgrpCode();
                            String score = totalscore.toString();
                            dref = FirebaseDatabase.getInstance().getReference();
                            long timeonRead = globalVariables.getTimespentonRead();
                            String totaltimeonread = timeonRead+ "";
                            long timeonQuiz = globalVariables.getTimespentonQuiz();
                            String totaltimeonQuiz = timeonQuiz + "";
                            String studentkey = globalVariables.getstudentKey();
                            createAssessment(grpCode, score, curr_topic, teacherkey, totaltimeonread, totaltimeonQuiz, studentkey);

                        }
                    }
                }
            }
        });
    }
    private void repeatquiz(String method){
        String todo = method;
        scored = scored - 5;
        globalVariables.setscore(scored);
        globalVariables.setonquiz("yes");
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Quiz.this);
        builder.setTitle("Information");
        if(todo == "wronganswer"){
            todo = "You have answer a question wrong. Read the lesson carefully";
        }else{
            todo = "You have consume your allotted time. \n 5% will be deducted to your percentage. \n Read the lesson carefully.";
        }
        builder.setMessage(todo)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Quiz.this, Read_Lesson_Screen.class);
                        Bundle extras = new Bundle();
                        extras.putString("lesson",s);
                        intent.putExtras(extras);
                        ad.dismiss();
                        startActivity(intent);
                        finish();
                    }
                });
        ad = builder.show();
    }
    private void getquestions(){
        dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(curr_topic).child("quiz").child("questions").child(questions_key.get(index));
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                q_type = newPost.get("q_type");
                if(Objects.equals(q_type.toString(), "True/False")){
                    question = newPost.get("q_question");
                    canswer = newPost.get("q_answer");
                    correctans[index] = canswer.toString();
                        questions = new TextView(Quiz.this);
                        questions.setText(index+1 + ". " + question.toString());
                        questions.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.questions));
                        questions.setTextColor(getResources().getColor(R.color.black));
                        quizlay.addView(questions);
                    quizlay.addView(rdgroup[index+1]);
                    RadioButton choices1 = new RadioButton(Quiz.this);
                    choices1.setText("True");
                    choices1.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.choices));
                    rdgroup[index+1].addView(choices1);
                    RadioButton choices2 = new RadioButton(Quiz.this);
                    choices2.setText("False");
                    choices2.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.choices));
                    rdgroup[index+1].addView(choices2);
                }else if(Objects.equals(q_type.toString(), "Multiple Choice")){
                    choices = new ArrayList<String>();
                    question = newPost.get("q_question");
                    canswer = newPost.get("q_answer");
                    correctans[index] = canswer.toString();
                    dref = database.getReference().child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson).child(curr_topic).child("quiz").child("questions").child(questions_key.get(index));
                    dref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                if(Objects.equals(d.getKey(), "q_answer") || Objects.equals(d.getKey(), "q_question") || Objects.equals(d.getKey(), "q_type")){

                                }else{
                                    choices.add(d.getValue().toString());
                                }
                            }
                            questions = new TextView(Quiz.this);
                            questions.setText(index+1 + ". " + question.toString());
                            questions.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    getResources().getDimension(R.dimen.questions));
                            questions.setTextColor(getResources().getColor(R.color.black));
                            quizlay.addView(questions);
                            quizlay.addView(rdgroup[index+1]);

                            for(int k = 0 ; k<=choices.size()-1 ; k++){
                                rdbtn = new RadioButton(Quiz.this);
                                rdbtn.setText(choices.get(k));
                                rdbtn.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                        getResources().getDimension(R.dimen.choices));
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
    private void createAssessment(String grpCode, String score, String curr_topic, String teacherkey, String timeonRead, String timeonQuiz, String studentkey) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String date_quiztaken = simpleDateFormat.format(new Date());
        Assessment user = new Assessment(score, timeonRead, timeonQuiz, date_quiztaken);
        dref.child("assessment").child(teacherkey).child(grpCode).child(studentkey).child(curr_topic).setValue(user);
        addUserChangeListener(studentkey, teacherkey,grpCode, curr_topic);

    }
    private void updateitemanalysis(String curr_subject, String curr_lesson, String curr_topic, String grpCode, String missed_key, String studkey, String teacherkey){
        dref.child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson)
                .child(curr_topic).child("quiz").child("item_analysis").child(grpCode).child(missed_key).child(studkey).setValue(1);
        addupdateanalysislistener(curr_subject,curr_lesson,curr_topic,grpCode,missed_key,studkey, teacherkey);
    }
    private void addupdateanalysislistener(String curr_subject, String curr_lesson, String curr_topic, String grpCode, String missed_key, String studkey, String teacherkey) {
        dref.child("subjects").child(teacherkey).child(curr_subject).child(curr_lesson)
                .child(curr_topic).child("quiz").child("item_analysis").child(grpCode).child(missed_key).addValueEventListener(new ValueEventListener() {
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
    private void addUserChangeListener(String studentkey, String teacherkey, String grpCode,String curr_topic) {
        dref.child("assessment").child(teacherkey).child(grpCode).child(studentkey).child(curr_topic).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Check", "Success");
                globalVariables.setscore(100);
                globalVariables.setTimespentonRead(0,"yes");
                globalVariables.setTimespentonQuiz(0,"yes");
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Quiz.this);
                builder.setTitle("Information");
                builder.setMessage("You have finished this topic! ")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Quiz.this, SelectedCourseScreen.class);
                                ad.dismiss();
                                startActivity(intent);
                                finish();
                            }
                        });
                ad = builder.show();
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
        timer2.cancel();
        if(ad != null){
            ad.dismiss();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        ad.dismiss();
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Information")
                .setMessage("Sorry you can't disregard your quiz.")
                .setPositiveButton(android.R.string.yes, null).create().show();
    }
}
