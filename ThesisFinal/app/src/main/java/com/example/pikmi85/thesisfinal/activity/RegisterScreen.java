package com.example.pikmi85.thesisfinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pikmi85.thesisfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterScreen extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        auth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    LinearLayout layout=new LinearLayout(RegisterScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(RegisterScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Enter Email Address");
                    layout.addView(tv);
                    Toast toast=new Toast(RegisterScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    LinearLayout layout=new LinearLayout(RegisterScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(RegisterScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Enter Password");
                    layout.addView(tv);
                    Toast toast=new Toast(RegisterScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    return;
                }
                if (password.length() < 6) {
                    LinearLayout layout=new LinearLayout(RegisterScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(RegisterScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Password too short. Enter a minimum of 6 characters");
                    layout.addView(tv);
                    Toast toast=new Toast(RegisterScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    return;
                }
                if(isEmailValid(email)){
                    btnSignUp.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    inputEmail.setEnabled(false);
                    inputPassword.setEnabled(false);
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterScreen.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                try
                                {
                                    throw task.getException();
                                }
                                catch (FirebaseAuthUserCollisionException existEmail)
                                {
                                    LinearLayout layout=new LinearLayout(RegisterScreen.this);
                                    layout.setBackgroundResource(R.color.darkorange);
                                    layout.setClipToOutline(true);
                                    TextView tv=new TextView(RegisterScreen.this);
                                    tv.setTextColor(Color.BLACK);
                                    tv.setTextSize(25);
                                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                    tv.setText("Email Already in Used.");
                                    layout.addView(tv);
                                    Toast toast=new Toast(RegisterScreen.this);
                                    toast.setView(layout);
                                    toast.setGravity(Gravity.CENTER, 0, 50);
                                    toast.show();
                                    btnSignUp.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    inputEmail.setEnabled(true);
                                    inputPassword.setEnabled(true);
                                }
                                catch (Exception e)
                                {
                                    Log.d("CHECK", "onComplete: " + e.getMessage());
                                }
                            }else{
                                auth.getCurrentUser().delete();
                                Intent intent = new Intent(getBaseContext(), RegisterInfoScreen.class);
                                Bundle extras = new Bundle();
                                extras.putString("EXTRA_EMAIL",email);
                                extras.putString("EXTRA_PASSWORD",password);
                                intent.putExtras(extras);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }else{
                    LinearLayout layout=new LinearLayout(RegisterScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);
                    TextView tv=new TextView(RegisterScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Invalid Email");
                    layout.addView(tv);
                    Toast toast=new Toast(RegisterScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    btnSignUp.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);
                }
            }
        });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterScreen.this,LoginScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);


    }
}
