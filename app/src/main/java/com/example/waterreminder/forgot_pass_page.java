package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_pass_page extends AppCompatActivity {

    private EditText username_forgot;

    private Button send_req_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_page);

        username_forgot = findViewById(R.id.username_forgot_field);

        send_req_btn = findViewById(R.id.rest_btn);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        send_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_forgot.getText().toString();
                String[] valid = username.split("@");


                if(TextUtils.isEmpty(username)){
                    Toast.makeText(forgot_pass_page.this,"Please Enter your Email ID",Toast.LENGTH_LONG).show();
                    username_forgot.setError("Email ID Required*");
                    username_forgot.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                    Toast.makeText(forgot_pass_page.this,"Enter valid email ID",Toast.LENGTH_LONG).show();
                    username_forgot.setError("Email ID not valid*");
                    username_forgot.requestFocus();
                }
                else if(!valid[1].equals("gmail.com")){
                    Toast.makeText(forgot_pass_page.this,"Gmail account required",Toast.LENGTH_LONG).show();
                    username_forgot.setError("Gmail account required*");
                    username_forgot.requestFocus();
                }
                else{
                    restpassword(username);
                }

            }

            private void restpassword(String username) {
                auth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(forgot_pass_page.this,"Check Your to change your password",Toast.LENGTH_LONG).show();;

                        }
                        else{
                            Toast.makeText(forgot_pass_page.this,"Something went Wrong! Try Again",Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }
        });

    }
}