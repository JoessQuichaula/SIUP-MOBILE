package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import zone.com.lightsweep.ShineAnimator;
import zone.com.lightsweep.ShineImageView;

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

    public static String APP_SHARED_PREFERENCE_NAME ="APP_SAVER";
    public static String APP_API_BASE_URL = "http://192.168.1.6/siup/public/";

    private SharedPreferences appDataSaver;
    ShineImageView shine;
    ShineImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shine = findViewById(R.id.imgAngolaFlag);
        logo = findViewById(R.id.app_logo);
        appDataSaver = getSharedPreferences(APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);

        new ShineAnimator()
                .setShineView(shine)
                .setInterpolator(new LinearInterpolator())
                .start();

        new ShineAnimator().
                setShineView(logo).
                setInterpolator(new LinearInterpolator()).
                start();

        //Start new Activity after 5 secs delayed
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNextActivity();
            }
        },2000);

    }
    private void showNextActivity(){
        if (!appDataSaver.getBoolean("isIntroductionScreensAlreadyDisplayed",false)){
            Intent intent = new Intent(SplashScreen.this, IntroductionScreen.class);
            startActivity(intent);
            finish();
        }
        else {
            if (appDataSaver.getBoolean("isUserLogged",false)){
                Intent intent = new Intent(SplashScreen.this, MainScreen.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();

            }
        }
    }


}
