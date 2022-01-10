package com.example.waterreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class stats_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);

        FloatingActionButton floatingActionButton = findViewById(R.id.add_time);

        RecyclerView recyclerView = findViewById(R.id.time_keeper);

        bottomNavigationView.setSelectedItemId(R.id.home_b);
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
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
                        return true;
                    case R.id.profile_b:
                        startActivity(new Intent(getApplicationContext(), profile_page.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                        return true;

                }

                return false;

            }
        });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(stats_page.this, reminder_set_alaram.class));
            }
        });




        RealmResults<timekeeper> timelist = realm.where(timekeeper.class).findAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Myadapter myadapter = new Myadapter(getApplicationContext(),timelist);
        recyclerView.setAdapter(myadapter);


         timelist.addChangeListener(new RealmChangeListener<RealmResults<timekeeper>>() {
             @Override
             public void onChange(@NotNull RealmResults<timekeeper> timekeepers) {
                 myadapter.notifyDataSetChanged();
             }
         });

    }
}