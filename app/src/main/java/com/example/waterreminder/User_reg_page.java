package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class User_reg_page extends AppCompatActivity {
    private EditText name_reg, age_reg, date_reg, height_reg, weight_reg, user_reg,pass_reg;
    private Button sign_up_btn;
    private TextView already_btn;

    private AutoCompleteTextView purpose_field, gender_field;
    private static final String TAG = "Regisiter_page";
    public static HashMap<String,Object> map  = new HashMap<>();

    ArrayAdapter<String> arrayAdapter, arrayAdapter1;

    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg_page);

        final Calendar  calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        name_reg = findViewById(R.id.name_reg_field);
        age_reg = findViewById(R.id.age_reg_field);
        date_reg = findViewById(R.id.date_reg_field);
        gender_field = findViewById(R.id.gender_field);
        height_reg = findViewById(R.id.height_reg_field);
        weight_reg = findViewById(R.id.weight_reg_field);
        purpose_field = findViewById(R.id.purpose_field);
        user_reg = findViewById(R.id.username_reg_field);
        pass_reg = findViewById(R.id.password_reg_field);

        sign_up_btn = findViewById(R.id.sign_up_btn);
        already_btn = findViewById(R.id.already_btn);

        // Date selection
        date_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(User_reg_page.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,onDateSetListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth+"/"+ month +"/"+ year;
                date_reg.setText(date);

            }
        };


        //Purpose field
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Blood sugar");
        arrayList.add("Kidney related issues");
        arrayList.add("Healthy Lifestyle");
        arrayList.add("Dehydration");
        arrayList.add("Pregnancy");

        arrayAdapter = new ArrayAdapter<>(this,R.layout.list_items,arrayList);
        purpose_field.setAdapter(arrayAdapter);


        //Gender field
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Male");
        arrayList1.add("Female");
        arrayAdapter1 = new ArrayAdapter<>(this,R.layout.list_items,arrayList1);
        gender_field.setAdapter(arrayAdapter1);


        // already navigation

        already_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_reg_page.this, login_page.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });


        //Sign_up

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_reg.getText().toString();
                String age = age_reg.getText().toString();
                String date = date_reg.getText().toString();
                String gender = gender_field.getText().toString();
                String height = height_reg.getText().toString();
                String weight = weight_reg.getText().toString();
                String purpose = purpose_field.getText().toString();
                String username  = user_reg.getText().toString();
                String password = pass_reg.getText().toString();

                String[] valid = username.split("@");

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(User_reg_page.this, "Name not entered",Toast.LENGTH_LONG).show();
                    name_reg.setError("Name required*");
                    name_reg.requestFocus();
                }

                else if(TextUtils.isEmpty(age)){
                    Toast.makeText(User_reg_page.this, "Age not entered",Toast.LENGTH_LONG).show();
                    age_reg.setError("Age required*");
                    age_reg.requestFocus();
                }
                else if(TextUtils.isEmpty(date)){
                    Toast.makeText(User_reg_page.this, "Date of birth not entered",Toast.LENGTH_LONG).show();
                    date_reg.setError("Date of birth required*");
                    date_reg.requestFocus();
                }
                else if(TextUtils.isEmpty(gender)){
                    Toast.makeText(User_reg_page.this, "Gender not selected",Toast.LENGTH_LONG).show();
                    gender_field.setError("Gender not selected*");
                    gender_field.requestFocus();
                }
                else if(TextUtils.isEmpty(height)){
                    Toast.makeText(User_reg_page.this, "Height not entered",Toast.LENGTH_LONG).show();
                    height_reg.setError("Height required*");
                    height_reg.requestFocus();
                }
                else if(TextUtils.isEmpty(weight)){
                    Toast.makeText(User_reg_page.this, "Weight not entered",Toast.LENGTH_LONG).show();
                    weight_reg.setError("Weight required*");
                    weight_reg.requestFocus();
                }
                else if(TextUtils.isEmpty(purpose)){
                    Toast.makeText(User_reg_page.this, "Purpose not selected",Toast.LENGTH_LONG).show();
                    purpose_field.setError("Purpose required*");
                    purpose_field.requestFocus();
                }
                else if(TextUtils.isEmpty(username)){
                    Toast.makeText(User_reg_page.this, "Email id not entered",Toast.LENGTH_LONG).show();
                    name_reg.setError("Email ID required*");
                    name_reg.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                    Toast.makeText(User_reg_page.this,"Enter valid email ID",Toast.LENGTH_LONG).show();
                    user_reg.setError("Email ID not valid*");
                    user_reg.requestFocus();
                }
                else if(!valid[1].equals("gmail.com")){
                    Toast.makeText(User_reg_page.this,"Gmail account required",Toast.LENGTH_LONG).show();
                    user_reg.setError("Gmail account required*");
                    user_reg.requestFocus();
                }
                else if(TextUtils.isEmpty(password)){

                        Toast.makeText(User_reg_page.this,"Enter Password",Toast.LENGTH_LONG).show();
                        pass_reg.setError("Password required*");
                        pass_reg.requestFocus();

                }

                else if(password.length()<8){
                    Toast.makeText(User_reg_page.this,"Password should be of more than 8 characters",Toast.LENGTH_LONG).show();
                    pass_reg.setError("Password should be of more than 8 characters*");
                    pass_reg.requestFocus();
                }
                else{
                    Reg_user(name,age,date,gender,height,weight,purpose,username,password);
                }




            }

            private void Reg_user(String name, String age, String date,String gender, String height, String weight, String purpose, String username, String password) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(User_reg_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(User_reg_page.this,"You Have been Registered",Toast.LENGTH_LONG).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();


                            map.put("Name",name);
                            map.put("Age",age);
                            map.put("Date",date);
                            map.put("Gender",gender);
                            map.put("Height",height);
                            map.put("Weight",weight);
                            map.put("Purpose",purpose);
                            map.put("Username",username);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered users");

                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(User_reg_page.this, "You Have been Registered. Please verify your email ID", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(User_reg_page.this,next_page_reg.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        finish();
                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    }
                                    else{
                                        Toast.makeText(User_reg_page.this,"Registration failed. Do it again",Toast.LENGTH_LONG).show();

                                    }
                                }

                            });


                        }                        else{
                            try {
                                throw Objects.requireNonNull(task.getException());
                            }
                            catch (FirebaseAuthWeakPasswordException e){
                                pass_reg.setError("Your Password is too weak, Kindly use a mix of alphabets and numericals");
                                pass_reg.requestFocus();

                            }
                            catch (FirebaseAuthInvalidCredentialsException e){
                                pass_reg.setError("Your email is invalid or already in use. kindly re-entry");
                                pass_reg.requestFocus();
                            }
                            catch (FirebaseAuthUserCollisionException e){
                                pass_reg.setError("The email is already registered with us");
                                pass_reg.requestFocus();
                            }
                            catch (Exception e){
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(User_reg_page.this,e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });



            }
        });




    }
}