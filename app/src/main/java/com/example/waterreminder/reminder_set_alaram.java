package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;

public class reminder_set_alaram extends AppCompatActivity {



    EditText setTime;
    Button set_alram;
    int thour,tmin, ehour,emin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_set_alaram);

        setTime = findViewById(R.id.settime);
        set_alram =  findViewById(R.id.set_alarm);


        Realm.init(getApplicationContext());
        Realm realmObject = Realm.getDefaultInstance();


        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(reminder_set_alaram.this,
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
                                    setTime.setText(hour12format.format(date));
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

        set_alram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = setTime.getText().toString();
                String[] convert_time = time.split(":");
                String[] min_convert = convert_time[convert_time.length-1].split(" ");
                int hours = Integer.parseInt(convert_time[0]);
                int min = Integer.parseInt(min_convert[0]);



                if(TextUtils.isEmpty(time)){
                    setTime.setError("Select Time");
                    setTime.requestFocus();

                }
                else{
                    realmObject.beginTransaction();
                    timekeeper timekeeper = realmObject.createObject(timekeeper.class);
                    timekeeper.setTime(time);
                    realmObject.commitTransaction();
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);

                    intent.putExtra(AlarmClock.EXTRA_HOUR,hours);
                    intent.putExtra(AlarmClock.EXTRA_MINUTES,min);
                   // intent.putExtra(AlarmClock.EXTRA_IS_PM,)
                    Toast.makeText(getApplicationContext(),"Reminder Set",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                    //startActivity(new Intent(reminder_set_alaram.this, stats_page.class));


                }
            }
        });



















    }


}