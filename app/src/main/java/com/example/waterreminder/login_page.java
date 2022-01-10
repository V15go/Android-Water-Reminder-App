package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {

    private EditText username_login, password_login;
    private Button login_btn;
    private TextView new_user_btn, forgot_pass_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        username_login = findViewById(R.id.username_field);
        password_login = findViewById(R.id.password_field);
        login_btn = findViewById(R.id.login_btn);
        new_user_btn = findViewById(R.id.new_user);
        forgot_pass_btn = findViewById(R.id.forget_btn);


        //new user button
        new_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, User_reg_page.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        forgot_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this,forgot_pass_page.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_l = username_login.getText().toString();
                String password = password_login.getText().toString();

                if(TextUtils.isEmpty(username_l)){
                    Toast.makeText(login_page.this,"Please Enter your username/Email ID", Toast.LENGTH_LONG).show();
                    username_login.setError("Email ID Required*");
                    username_login.requestFocus();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(login_page.this,"Please Enter your Password", Toast.LENGTH_LONG).show();
                    password_login.setError("Password Required*");
                    password_login.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(username_l).matches()){
                    Toast.makeText(login_page.this,"Enter valid email ID",Toast.LENGTH_LONG).show();
                    username_login.setError("Email ID not valid*");
                    username_login.requestFocus();
                }
                else{
                    login_user(username_l,password);
                }
            }

            private void login_user(String username_1, String password) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(username_1,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login_page.this,"Welcome Back!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(login_page.this, home_page.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            finish();
                        }
                        else{
                            Toast.makeText(login_page.this, "Something went wrong! Ensure that the email or password is correct or Verfication of email may not be done", Toast.LENGTH_LONG).show();
                            username_login.setError("Check your username");
                            username_login.requestFocus();
                            password_login.setError("Check your Password");
                            password_login.requestFocus();
                        }

                    }
                });


            }
        });



    }
}