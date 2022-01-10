package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class next_page_reg extends AppCompatActivity {
    EditText start_time, end_time;
    int thour,tmin, ehour,emin;
    TextView dive_btn, water_amt_req;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page_reg);



        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        water_amt_req = findViewById(R.id.amt_of_water);

        dive_btn = findViewById(R.id.dive_btn);


//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Re")


        

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(next_page_reg.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                thour = hourOfDay;
                                tmin = minute;
                                String time = thour + ":"+tmin;
                                SimpleDateFormat hours24format = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = hours24format.parse(time);
                                    SimpleDateFormat hour12format = new SimpleDateFormat("hh:mm aa");
                                    start_time.setText(hour12format.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false


                        );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(thour,tmin);
                timePickerDialog.show();
            }
        });


        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(next_page_reg.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ehour = hourOfDay;
                                emin = minute;
                                String time = ehour + ":"+emin;
                                SimpleDateFormat hours24format = new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = hours24format.parse(time);
                                    SimpleDateFormat hour12format = new SimpleDateFormat("hh:mm aa");
                                    end_time.setText(hour12format.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false


                );

                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(ehour,emin);
                timePickerDialog.show();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String purpose = snapshot.child("Purpose").getValue().toString();
                    String gender = snapshot.child("Gender").getValue().toString();
                    int age = Integer.parseInt(snapshot.child("Age").getValue().toString());
                    if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle")) && gender.equals("Male") && (age>=9 && age<=13)){
                        water_amt_req.setText("2.5");
                    }
                    else  if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle")) && gender.equals("Male") && (age>=14 && age<=18)){
                        water_amt_req.setText("3.3");
                    }
                    else  if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle")) && gender.equals("Male") && (age>=19 && age<=50)){
                        water_amt_req.setText("4");
                    }
                    else  if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle")) && gender.equals("Male") && (age>=50)){
                        water_amt_req.setText("4");
                    }
                    else  if((purpose.equals("Kidney related issues")) && gender.equals("Male")){
                        water_amt_req.setText("3");
                    }
                    else  if((purpose.equals("Kidney related issues")) && gender.equals("Female")){
                        water_amt_req.setText("2.5");
                    }
                   else if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle") || purpose.equals("Pregnancy")) && gender.equals("Female") && (age>=9 && age<=13)){
                        water_amt_req.setText("2");
                    }
                    else  if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle") || purpose.equals("Pregnancy")) && gender.equals("Female") && (age>=14 && age<=18)){
                        water_amt_req.setText("2.5");
                    }
                    else  if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle") || purpose.equals("Pregnancy")) && gender.equals("Female") && (age>=19 && age<=50)){
                        water_amt_req.setText("3");
                    }
                    else  if((purpose.equals("Blood Sugar") || purpose.equals("Dehydration") || purpose.equals("Healthy Lifestyle") || purpose.equals("Pregnancy")) && gender.equals("Female") && (age>=50)){
                        water_amt_req.setText("2.5");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Registered users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());




        dive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_reg_page.map.put("Required Water",water_amt_req.getText().toString());
                User_reg_page.map.put("Start time", start_time.getText().toString());
                User_reg_page.map.put("End time",end_time.getText().toString());
                reference.setValue(User_reg_page.map);
                Intent intent = new Intent(next_page_reg.this, home_page.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);

            }
        });

    }
}