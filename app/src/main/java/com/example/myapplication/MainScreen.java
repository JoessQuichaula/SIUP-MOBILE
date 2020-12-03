package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.bottom_ui.ActivitiesFragment;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class MainScreen extends AppCompatActivity  {


    BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 102:
                if (resultCode == RESULT_OK && data != null && data.getData() != null){

                }
                //ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                //Toast.makeText(this, files.get(0).getPath(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Request Code Accepted", Toast.LENGTH_SHORT).show();
                break;
        }
       // Toast.makeText(this, "yeh", Toast.LENGTH_SHORT).show();


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