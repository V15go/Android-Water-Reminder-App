package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class home_page extends AppCompatActivity {
    private TextView user_name;
    TextView total_water, left_water, goal_view;
    Button add_btn,undo_btn;

    public static final String SHARED = "shared";
    public static final  String TEXT = "text";

    private FirebaseDatabase firebaseDatabase;
    static double water = 0;
    private DatabaseReference reference;
    SharedPreferences sharedPreferences ;
    SharedPreferences sharedPreferences2 ;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        user_name = findViewById(R.id.user_name);
        add_btn = findViewById(R.id.add_btn);
        left_water = findViewById(R.id.left_water);
        total_water = findViewById(R.id.total_water);
        left_water= findViewById(R.id.left_water);
        goal_view = findViewById(R.id.goal_view);
        undo_btn = findViewById(R.id.undo_btn);
        bottomNavigationView.setSelectedItemId(R.id.home_b);



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered users");
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    user_name.setText(snapshot.child("Name").getValue().toString());
                    //goal_view.setText(snapshot.child("Required Water").getValue().toString());
                    int water =Integer.parseInt(Objects.requireNonNull(snapshot.child("Required Water").getValue()).toString());
                    water*=2;
                    String res = "";
                    res += water;
                    goal_view.setText(res);
                    total_water.setText(res);
                    left_water.setText(res);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_b:
                        return true;
                    case R.id.stats_b:
                        startActivity(new Intent(getApplicationContext(),stats_page.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                        return true;
                    case R.id.profile_b:
                        startActivity(new Intent(getApplicationContext(),profile_page.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                        return true;

                }

                return false;

            }
        });










        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());




        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int water = Integer.parseInt(total_water.getText().toString());

                water-=1;
                if(water == 0){
                    new AlertDialog.Builder(home_page.this).setIcon(R.drawable.ic_baseline_thumb_up_24)
                            .setTitle("Hurray!")
                            .setMessage("You completed your water intake today")
                            .setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {



                                }
                            }).show();
                }

                sharedPreferences = getSharedPreferences(SHARED,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(goal_view.getText().toString(),total_water.getText().toString());
                editor.putString(goal_view.getText().toString(),left_water.getText().toString());

                String res = "";
                res += water;
                total_water.setText(res);
                left_water.setText(res);

            }
        });
        undo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int water = Integer.parseInt(total_water.getText().toString());
                water+=1;
               sharedPreferences2 = getSharedPreferences(SHARED,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putString(goal_view.getText().toString(),total_water.getText().toString());
                editor.putString(goal_view.getText().toString(),left_water.getText().toString());

                String res = "";
                res += water;
                total_water.setText(res);
                left_water.setText(res);
            }
        });






        if(currentTime.equals("00:00")){
            total_water.setText(goal_view.getText());
            left_water.setText(goal_view.getText());
        }











    }


}