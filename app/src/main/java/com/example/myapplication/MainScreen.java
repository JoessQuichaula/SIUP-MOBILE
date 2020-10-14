package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.bottom_ui.ActivitiesFragment;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity  {


    BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        bottomNavigationView = findViewById(R.id.bottomNavbar);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomNavbar_container,new HomeFragment())
                .commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment = new HomeFragment();
                switch (menuItem.getItemId()){
                    case R.id.activitypage:
                        selectedFragment = new ActivitiesFragment();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.bottomNavbar_container,selectedFragment)
                        .commit();
                return true;
            }
        });



    }

}