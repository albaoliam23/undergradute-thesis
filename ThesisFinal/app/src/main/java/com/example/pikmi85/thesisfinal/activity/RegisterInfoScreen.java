package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.User;

import com.example.pikmi85.thesisfinal.classes;
import com.example.pikmi85.thesisfinal.globalVariables;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class RegisterInfoScreen extends AppCompatActivity {

    private static final String TAG = RegisterInfoScreen.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputfirstName, inputlastName;
    private Button btnSave;
    RadioGroup rdgroup;
    RadioButton rdbutton0, rdbutton1,rdbutton2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref;
    FirebaseUser user;
    private FirebaseAuth auth;
    String fname, lname, email1, gender, utype, id, status;
    String email, password;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register_info_screen);
        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputfirstName = (EditText) findViewById(R.id.firstname);
        inputlastName = (EditText) findViewById(R.id.lastname);
        btnSave = (Button) findViewById(R.id.btn_save);
        auth = FirebaseAuth.getInstance();
        rdgroup = (RadioGroup) findViewById(R.id.radioGroup1);
        rdbutton0 = (RadioButton) findViewById(R.id.radio0);
        rdbutton1 = (RadioButton) findViewById(R.id.radio1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        dref = FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        email = extras.getString("EXTRA_EMAIL");
        password = extras.getString("EXTRA_PASSWORD");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputfirstName.setEnabled(false);
                inputlastName.setEnabled(false);
                btnSave.setVisibility(View.INVISIBLE);
                rdgroup.setEnabled(false);
                fname = inputfirstName.getText().toString();
                lname = inputlastName.getText().toString();
                utype = "Student";
                status = "firsttimelogin";
                email1 = email;
                int selectedId = rdgroup.getCheckedRadioButtonId();
                rdbutton2 = (RadioButton) findViewById(selectedId);
                gender = rdbutton2.getText().toString();
                if(fname.isEmpty() || lname.isEmpty() || gender.isEmpty()){
                    LinearLayout layout=new LinearLayout(RegisterInfoScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(RegisterInfoScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Empty Field/s");
                    layout.addView(tv);
                    Toast toast=new Toast(RegisterInfoScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    inputfirstName.setEnabled(true);
                    inputlastName.setEnabled(true);
                    btnSave.setVisibility(View.VISIBLE);
                    rdgroup.setEnabled(true);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterInfoScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        inputfirstName.setEnabled(true);
                                        inputlastName.setEnabled(true);
                                        btnSave.setVisibility(View.VISIBLE);
                                        rdgroup.setEnabled(true);
                                        LinearLayout layout=new LinearLayout(RegisterInfoScreen.this);
                                        layout.setBackgroundResource(R.color.darkorange);
                                        layout.setClipToOutline(true);
                                        TextView tv=new TextView(RegisterInfoScreen.this);
                                        tv.setTextColor(Color.BLACK);
                                        tv.setTextSize(25);
                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                        tv.setText("Registration Failed. Email is already used.");
                                        layout.addView(tv);
                                        Toast toast=new Toast(RegisterInfoScreen.this);
                                        toast.setView(layout);
                                        toast.setGravity(Gravity.CENTER, 0, 50);
                                        toast.show();
                                        Intent intent = new Intent(RegisterInfoScreen.this, RegisterScreen.class);
                                        startActivity(intent);
                                        finish();
                                    } else if(task.isSuccessful()) {
                                        id = dref.push().getKey();
                                        user = auth.getCurrentUser();
                                        createUser(fname, lname, utype, email1, id, gender);
                                        finish();
                                    }
                                }
                            });
                }
            }
                });

    }
    private void createUser(String fname, String lname, String utype, String email1, String id, String gender) {
        User user = new User(fname, lname, utype, email1, gender);
        dref.child("members").child(id).setValue(user);
        addUserChangeListener(id, fname, email1);
    }
    private void addUserChangeListener(String id, String fname, String email1) {
        final String studentkey = id;
        final String fname1 = fname;
        final String email2 = email1;
        dref.child("members").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                globalVariables.setname(fname1);
                globalVariables.setEmail(email2);
                globalVariables.setstudentKey(studentkey);
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        LinearLayout layout=new LinearLayout(RegisterInfoScreen.this);
                        layout.setBackgroundResource(R.color.darkorange);
                        layout.setClipToOutline(true);
                        TextView tv=new TextView(RegisterInfoScreen.this);
                        tv.setTextColor(Color.BLACK);
                        tv.setTextSize(25);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setText(getString(R.string.verify_first));
                        layout.addView(tv);
                        Toast toast=new Toast(RegisterInfoScreen.this);
                        toast.setView(layout);
                        toast.setGravity(Gravity.CENTER, 0, 50);
                        toast.show();
                    }
                });
                boolean isVerified = user.isEmailVerified();
                if(!isVerified){
                    auth.signOut();
                    Intent intent = new Intent(RegisterInfoScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(RegisterInfoScreen.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                }}

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed", error.toException());
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterInfoScreen.this,RegisterScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
