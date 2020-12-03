package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.example.myapplication.registerScreens.Register_Screen;

public class SplashScreen extends AppCompatActivity {



    //Background Gradient Animation
    /*
        AnimationDrawable animationDrawable;
        RelativeLayout relativeLayout;

        relativeLayout = findViewById(R.id.tela_splash);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Start new Activity after 5 secs delayed
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Test.class);
                startActivity(intent);

            }
        },5000);

    }
}
