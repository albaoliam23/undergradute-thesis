package com.example.pikmi85.thesisfinal.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.example.pikmi85.thesisfinal.R;

public class ResetPasswordScreen extends AppCompatActivity {
    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_screen);
        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    LinearLayout layout=new LinearLayout(ResetPasswordScreen.this);
                    layout.setBackgroundResource(R.color.darkorange);
                    layout.setClipToOutline(true);

                    TextView tv=new TextView(ResetPasswordScreen.this);
                    tv.setTextColor(Color.BLACK);
                    tv.setTextSize(25);

                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setText("Enter Your Registered Email Address.");

                    layout.addView(tv);

                    Toast toast=new Toast(ResetPasswordScreen.this);
                    toast.setView(layout);
                    toast.setGravity(Gravity.CENTER, 0, 50);
                    toast.show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    LinearLayout layout=new LinearLayout(ResetPasswordScreen.this);
                                    layout.setBackgroundResource(R.color.darkorange);
                                    layout.setClipToOutline(true);

                                    TextView tv=new TextView(ResetPasswordScreen.this);
                                    tv.setTextColor(Color.BLACK);
                                    tv.setTextSize(25);

                                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                    tv.setText("We have sent you instructions to reset your password!");

                                    layout.addView(tv);

                                    Toast toast=new Toast(ResetPasswordScreen.this);
                                    toast.setView(layout);
                                    toast.setGravity(Gravity.CENTER, 0, 50);
                                    toast.show();
                                } else {
                                    LinearLayout layout=new LinearLayout(ResetPasswordScreen.this);
                                    layout.setBackgroundResource(R.color.darkorange);
                                    layout.setClipToOutline(true);

                                    TextView tv=new TextView(ResetPasswordScreen.this);
                                    tv.setTextColor(Color.BLACK);
                                    tv.setTextSize(25);

                                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                                    tv.setText("Failed to send reset email!");

                                    layout.addView(tv);

                                    Toast toast=new Toast(ResetPasswordScreen.this);
                                    toast.setView(layout);
                                    toast.setGravity(Gravity.CENTER, 0, 50);
                                    toast.show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

}

