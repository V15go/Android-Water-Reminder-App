package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_page extends AppCompatActivity {

    private TextView pro_name, pro_age, pro_dob, pro_weight, pro_height, pro_purpose;
    private Button sign_out;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi1);
        pro_name = findViewById(R.id.profile_name);
        pro_age = findViewById(R.id.profile_age);
        pro_dob = findViewById(R.id.profile_dob);
        pro_weight = findViewById(R.id.profile_weight);
        pro_height = findViewById(R.id.profile_height);
        pro_purpose = findViewById(R.id.profile_purpose);
        sign_out = findViewById(R.id.profile_sign_out);



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Registered users");

        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    pro_name.setText(snapshot.child("Name").getValue().toString());
                    pro_age.setText(snapshot.child("Age").getValue().toString());
                    pro_dob.setText(snapshot.child("Date").getValue().toString());
                    pro_weight.setText(snapshot.child("Weight").getValue().toString());
                    pro_height.setText(snapshot.child("Height").getValue().toString());
                    pro_purpose.setText(snapshot.child("Purpose").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile_page.this,login_page.class));
            }
        });








        bottomNavigationView.setSelectedItemId(R.id.profile_b);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_b:
                        startActivity(new Intent(getApplicationContext(),home_page.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                        return true;
                    case R.id.stats_b:
                        startActivity(new Intent(getApplicationContext(),stats_page.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                        return true;
                    case R.id.profile_b:
                        return true;

                }

                return false;

            }
        });








    }
}