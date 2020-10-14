package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.ui.main.SectionsPagerAdapter;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class ApresentationScreen extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentation_screen);




    }

    @Override
    public void currentFragmentPosition(int position) {

    }
}