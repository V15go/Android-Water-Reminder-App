package com.example.waterreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        LottieAnimationView lottieAnimationView = findViewById(R.id.anim);
        TextView name_v15 = findViewById(R.id.name_v15go);

        lottieAnimationView.animate().translationY(1600).setDuration(1000).setStartDelay(4000);
        name_v15.animate().translationY(1600).setDuration(1000).setStartDelay(4000);


       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i = new Intent(splash_screen.this,login_page.class);
               startActivity(i);
               finish();
           }
       },5000);


    }
}