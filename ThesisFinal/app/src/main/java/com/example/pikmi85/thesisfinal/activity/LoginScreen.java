package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pikmi85.thesisfinal.R;
import com.example.pikmi85.thesisfinal.globalVariables;
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
import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref;
    private ProgressBar progressBar;
    Button btnSignup, btnLogin, btnReset;
    Object email2, fname, type;
    String email;
    List<String> classes;
    FirebaseUser user;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginScreen.this, MainScreen.class));
            finish();
        }
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.activity_login_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        classes = new ArrayList<>();
        logo = (ImageView) findViewById(R.id.logo);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        auth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, ResetPasswordScreen.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    LinearLayout layout=new LinearLayout(LoginScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(LoginScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Enter Email Address");
                    layout.addView(tv);
                    Toast toast=new Toast(LoginScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    LinearLayout layout=new LinearLayout(LoginScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(LoginScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Enter Password");
                    layout.addView(tv);
                    Toast toast=new Toast(LoginScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    return;
                }
                btnLogin.setVisibility(View.GONE);
                inputEmail.setEnabled(false);
                inputPassword.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    progressBar.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                    inputEmail.setEnabled(true);
                                    inputPassword.setEnabled(true);
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        LinearLayout layout=new LinearLayout(LoginScreen.this);
                                        layout.setBackgroundResource(R.color.darkorange);
                                        layout.setClipToOutline(true);
                                        TextView tv=new TextView(LoginScreen.this);
                                        tv.setTextColor(Color.BLACK);
                                        tv.setTextSize(25);
                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                        tv.setText(getString(R.string.auth_failed));
                                        layout.addView(tv);
                                        Toast toast=new Toast(LoginScreen.this);
                                        toast.setView(layout);
                                        toast.setGravity(Gravity.CENTER, 0, 50);
                                        toast.show();
                                    }
                                } else {
                                    user = auth.getCurrentUser();
                                    boolean isVerified = user.isEmailVerified();
                                    if(!isVerified){
                                        LinearLayout layout=new LinearLayout(LoginScreen.this);
                                        layout.setBackgroundResource(R.color.darkorange);
                                        layout.setClipToOutline(true);
                                        TextView tv=new TextView(LoginScreen.this);
                                        tv.setTextColor(Color.BLACK);
                                        tv.setTextSize(25);
                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                        tv.setText(getString(R.string.auth_failed2));
                                        layout.addView(tv);
                                        Toast toast=new Toast(LoginScreen.this);
                                        toast.setView(layout);
                                        toast.setGravity(Gravity.CENTER, 0, 50);
                                        toast.show();
                                        auth.signOut();
                                        progressBar.setVisibility(View.GONE);
                                        btnLogin.setVisibility(View.VISIBLE);
                                        inputEmail.setEnabled(true);
                                        inputPassword.setEnabled(true);
                                    }else{
                                        dref = database.getReference().child("members");
                                        dref.addChildEventListener(new ChildEventListener() {
                                            // Retrieve new posts as they are added to Firebase
                                            @Override
                                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                                                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                                                email2 = newPost.get("email");
                                                if (Objects.equals(email2.toString(), email)) {
                                                    type = newPost.get("type");
                                                    if(Objects.equals(type.toString(), "Teacher")){
                                                        LinearLayout layout=new LinearLayout(LoginScreen.this);
                                                        layout.setBackgroundResource(R.color.darkorange);
                                                        layout.setClipToOutline(true);
                                                        TextView tv=new TextView(LoginScreen.this);
                                                        tv.setTextColor(Color.BLACK);
                                                        tv.setTextSize(25);
                                                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                                        tv.setText(getString(R.string.auth_failed3));
                                                        layout.addView(tv);
                                                        Toast toast=new Toast(LoginScreen.this);
                                                        toast.setView(layout);
                                                        toast.setGravity(Gravity.CENTER, 0, 50);
                                                        toast.show();
                                                        auth.signOut();
                                                        progressBar.setVisibility(View.GONE);
                                                        btnLogin.setVisibility(View.VISIBLE);
                                                        inputEmail.setEnabled(true);
                                                        inputPassword.setEnabled(true);
                                                    }else{
                                                        String studentkey = snapshot.getKey();
                                                        fname = newPost.get("firstname");
                                                        globalVariables.setname(fname.toString());
                                                        globalVariables.setEmail(email);
                                                        globalVariables.setstudentKey(studentkey);
                                                        FileCacher<String> name = new FileCacher<String>(LoginScreen.this, "name");
                                                        FileCacher<Object> email = new FileCacher<Object>(LoginScreen.this, "email");
                                                        FileCacher<String> studkey = new FileCacher<String>(LoginScreen.this, "studkey");
                                                        try{
                                                            name.writeCache(fname.toString());
                                                            email.writeCache(email2);
                                                            studkey.writeCache(studentkey);
                                                        }catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        progressBar.setVisibility(View.GONE);
                                                        Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }

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
                                            //... ChildEventListener also defines onChildChanged, onChildRemoved,
                                            //    onChildMoved and onCanceled, covered in later sections.
                                        });
                                    }


                                }
                            }
                        });
            }
        });
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(LoginScreen.this,LoginScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                }).create().show();
    }
}
