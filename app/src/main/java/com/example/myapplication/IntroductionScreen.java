package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.example.myapplication.home_ui.services.ServiceScreen;
import com.example.myapplication.models.IntroductionItem;
import com.example.myapplication.registerScreens.RegisterScreen;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ui.main.SectionsPagerAdapter;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class IntroductionScreen extends AppCompatActivity {


    ViewPager viewPager;
    IntroductionPagerAdapter introductionPagerAdapter;
    List<IntroductionItem> introductionItems;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    TabLayout tabIndicator;
    TextView btnNext;
    int position = 0;
    //
    //
    //
    //
    Animation animation;
    Animation animationForImg;
    Button btnCreateAccount;
    TextView txtAlreadyHaveAccount;
    TextView btnEnter;
    private SharedPreferences appDataSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentation_screen);
        appDataSaver = getSharedPreferences(SplashScreen.APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);

        btnNext = findViewById(R.id.btnNext);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        txtAlreadyHaveAccount = findViewById(R.id.txtAlreadyHaveAccount);
        btnEnter = findViewById(R.id.btnEnter);
        tabIndicator = findViewById(R.id.tabIndicator);
        relativeLayout = findViewById(R.id.introLayout);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        String title1 = getResources().getString(R.string.introTitle1);
        String title2 = getResources().getString(R.string.introTitle2);
        String title3 = getResources().getString(R.string.introTitle3);

        String body1 = getResources().getString(R.string.introBody1);
        String body2 = getResources().getString(R.string.introBody2);
        String body3 = getResources().getString(R.string.introBody3);

        viewPager = findViewById(R.id.introductionViewPager);
        introductionItems = new ArrayList<>();
        introductionItems.add(new IntroductionItem(R.drawable.intro1,title1,body1));
        introductionItems.add(new IntroductionItem(R.drawable.intro2,title2,body2));
        introductionItems.add(new IntroductionItem(R.drawable.intro3,title3,body3));
        introductionPagerAdapter = new IntroductionPagerAdapter(IntroductionScreen.this,introductionItems);
        viewPager.setAdapter(introductionPagerAdapter);
        tabIndicator.setupWithViewPager(viewPager);
        animation = AnimationUtils.loadAnimation(IntroductionScreen.this,R.anim.anim_introduction);
        animationForImg = AnimationUtils.loadAnimation(IntroductionScreen.this,R.anim.anim_intro_img);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = getCurrentView(viewPager).findViewById(R.id.apresentationImg);
                imageView.setVisibility(View.VISIBLE);
                TextView textView =  getCurrentView(viewPager).findViewById(R.id.apresentationTitle);
                TextView textView1 = getCurrentView(viewPager).findViewById(R.id.apresentationBody);
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                textView.startAnimation(animation);
                textView1.startAnimation(animation);
                imageView.startAnimation(animationForImg);
            }
        },500);


        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDataSaver
                        .edit()
                        .putBoolean("isIntroductionScreensAlreadyDisplayed",true)
                        .apply();
                Intent intent = new Intent(IntroductionScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDataSaver
                        .edit()
                        .putBoolean("isIntroductionScreensAlreadyDisplayed",true)
                        .apply();
                Intent intent = new Intent(IntroductionScreen.this, RegisterScreen.class);
                startActivity(intent);
                finish();
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == (introductionItems.size()-1)){
                    lastPageHandler();
                }else {
                    position = tab.getPosition();
                    btnNext.setVisibility(View.VISIBLE);
                    btnCreateAccount.setVisibility(View.INVISIBLE);
                    txtAlreadyHaveAccount.setVisibility(View.INVISIBLE);
                    btnEnter.setVisibility(View.INVISIBLE);
                }
                ImageView imageView = getCurrentView(viewPager).findViewById(R.id.apresentationImg);
                imageView.setVisibility(View.VISIBLE);
                TextView textView =  getCurrentView(viewPager).findViewById(R.id.apresentationTitle);
                TextView textView1 = getCurrentView(viewPager).findViewById(R.id.apresentationBody);
                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.VISIBLE);
                textView.startAnimation(animation);
                textView1.startAnimation(animation);
                imageView.startAnimation(animationForImg);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnNext.setOnClickListener(v -> {
            if (position == (introductionItems.size()-1)){
                lastPageHandler();
            }else {
                ++position;
                viewPager.setCurrentItem(position);
            }

        });


    }

    private void lastPageHandler(){
        btnNext.setVisibility(View.INVISIBLE);
        btnCreateAccount.setVisibility(View.VISIBLE);
        txtAlreadyHaveAccount.setVisibility(View.VISIBLE);
        btnEnter.setVisibility(View.VISIBLE);
        txtAlreadyHaveAccount.setAnimation(animation);
        btnEnter.setAnimation(animation);
        btnCreateAccount.setAnimation(animation);
        animation.start();
    }

    public View getCurrentView(ViewPager viewPager) {

        try {
            final int currentItem = viewPager.getCurrentItem();
            for (int i = 0; i < viewPager.getChildCount(); i++) {
                final View child = viewPager.getChildAt(i);
                final ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) child.getLayoutParams();

                Field f = layoutParams.getClass().getDeclaredField("position"); //NoSuchFieldException
                f.setAccessible(true);
                int position = (Integer) f.get(layoutParams); //IllegalAccessException

                if (!layoutParams.isDecor && currentItem == position) {
                    return child;
                }

            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Toast.makeText(this, "falhou", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


}