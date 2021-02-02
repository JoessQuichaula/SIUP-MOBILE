package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.bottom_ui.ActivitiesFragment;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.example.myapplication.home_ui.services.ServiceScreen4;
import com.example.myapplication.models.UserItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreen extends AppCompatActivity {


    public UserItem userItem;
    BottomNavigationView bottomNavigationView;
    public Fragment selectedFragment;
    Fragment lastSelectedFragment;
    String fragmentTag;
    String THEME_SELECTION;
    int bottomNavItemId;
    FrameLayout bottomNavContainer;
    public RetrofitConfig retrofitConfig;
    private final static int REQUEST_CODE = 104;
    boolean isWhiteTheme = true;
    @Override
    public void onBackPressed() {
        if (selectedFragment instanceof HomeFragment) {
            HomeFragment homeFragment = ((HomeFragment) selectedFragment);
            if (homeFragment.getCurrentFragmentInViewPager() instanceof ServicesContainer) {
                if (homeFragment.getCurrentFragmentInViewPager().getChildFragmentManager().getBackStackEntryCount() == 0){
                    homeFragment.tabNavBarhome_viewpager.setCurrentItem(0);
                }
                else if (homeFragment.getCurrentFragmentInViewPager().getChildFragmentManager().getBackStackEntryCount() == 4 ){
                    ServicesContainer servicesContainer = (ServicesContainer)homeFragment.getCurrentFragmentInViewPager();
                    ServiceScreen4 serviceScreen4 = (ServiceScreen4) servicesContainer.getChildFragmentManager().findFragmentByTag("serviceScreen4");
                    serviceScreen4.exitToSelectedFragmentOrBackStack();

                }
                else if (homeFragment.getCurrentFragmentInViewPager().getChildFragmentManager().getBackStackEntryCount() == 2){
                    homeFragment.tabNavBarhome_viewpager.setSwipeLocked(false);
                    homeFragment
                            .getCurrentFragmentInViewPager()
                            .getChildFragmentManager()
                            .popBackStack();
                }
                else {
                    homeFragment
                            .getCurrentFragmentInViewPager()
                            .getChildFragmentManager()
                            .popBackStack();
                }
            }
            else if (homeFragment.getCurrentFragmentInViewPager() instanceof FeedNewsContainer){
                if (homeFragment.getCurrentFragmentInViewPager().getChildFragmentManager().getBackStackEntryCount() == 0){
                    finish();
                }
                else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    toolbar.setTitle(null);
                    findViewById(R.id.appbar).setElevation(0);
                    homeFragment.tabNavBarhome_viewpager.setSwipeLocked(false);
                    homeFragment
                            .getCurrentFragmentInViewPager()
                            .getChildFragmentManager()
                            .popBackStack();

                }
            }
            else{
                homeFragment.tabNavBarhome_viewpager.setCurrentItem(0);
            }
        }
        else {
            super.onBackPressed();
        }
    }

    public Toolbar toolbar;
    public AppBarLayout appBarLayout;
    SharedPreferences appDataSaver;
    SharedPreferences.Editor editor;
    MenuItem itemTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //APP THEME CONFIG
        itemTheme = findViewById(R.id.itemTheme);
        appDataSaver = getSharedPreferences(SplashScreen.APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        isWhiteTheme = appDataSaver.getBoolean("isWhiteTheme",true);
        if (isWhiteTheme){
            setTheme(R.style.WhiteTheme);
            THEME_SELECTION = "Modo Escuro";
        }
        else{
            setTheme(R.style.DarkTheme);
            THEME_SELECTION = "Modo Claro";
        }

        //APP CREATE VIEW
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //INSTANCE OF ATTR
        toolbar = findViewById(R.id.toolbar);
        retrofitConfig = new RetrofitConfig(MainScreen.this,appDataSaver.getString("Auth",null));
        bottomNavContainer = findViewById(R.id.bottomNavbar_container);
        bottomNavigationView = findViewById(R.id.bottomNavbar);
        appBarLayout = findViewById(R.id.appbar);


        //TOOLBAR CONFIG
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.showOverflowMenu();

        RequestPermission();

        //RETROFIT CONFIG CLASS
        retrofitConfig.initRetrofit();

        //GET USER CREDENTIALS SAVED IN SHARED_PREFERENCES
        Gson gson = new Gson();
        String userJson = appDataSaver.getString("user",null);
        userItem = gson.fromJson(userJson,UserItem.class);


        selectedFragment = new HomeFragment();
        lastSelectedFragment = selectedFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.bottomNavbar_container,selectedFragment,"homeFragment")
                .commit();



        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.homepage:
                        selectedFragment = new HomeFragment();
                        fragmentTag = "homeFragment";
                        bottomNavItemId = menuItem.getItemId();
                        break;
                    case R.id.activitypage:
                        selectedFragment = new ActivitiesFragment();
                        fragmentTag = "activitiesFragment";
                        bottomNavItemId = menuItem.getItemId();
                        break;
                    case R.id.notpage:
                        Toast.makeText(MainScreen.this, "Você Ainda", Toast.LENGTH_SHORT).show();
                        return false;
                    case R.id.perfilpage:
                        Toast.makeText(MainScreen.this, "Você Ainda", Toast.LENGTH_SHORT).show();
                        return false;
                }

                if (isServiceScreen4TheCurrentScreen()){return false;}
                if (findViewById(R.id.appbar).getVisibility() == View.GONE)
                findViewById(R.id.appbar).setVisibility(View.VISIBLE);

                return changeFragment();
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "true yes", Toast.LENGTH_SHORT).show();
                }
        }
    }
    private boolean isServiceScreen4TheCurrentScreen(){
        if (lastSelectedFragment instanceof HomeFragment) {
            HomeFragment homeFragment =
                    (HomeFragment) getSupportFragmentManager()
                            .findFragmentByTag("homeFragment");

            if (homeFragment.getCurrentFragmentInViewPager() instanceof ServicesContainer){
                ServicesContainer servicesContainer = (ServicesContainer)homeFragment.getCurrentFragmentInViewPager();
                ServiceScreen4 serviceScreen4 = (ServiceScreen4) servicesContainer
                                                                .getChildFragmentManager()
                                                                .findFragmentByTag("serviceScreen4");
                if (serviceScreen4 != null && serviceScreen4.isCurrentScreenOnServiceScreen4){
                    serviceScreen4.exitToSelectedFragmentOrBackStack(bottomNavItemId);
                    return serviceScreen4.isCurrentScreenOnServiceScreen4;
                }

            }
        }
        return false;
    }
    private boolean changeFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomNavbar_container, selectedFragment,fragmentTag)
                .commit();
        retrofitConfig.isFailureThreadListened = false;
        appBarLayout.setElevation(0);
        //Toast.makeText(MainScreen.this, "0: "+retrofitConfig.isFailureThreadListened, Toast.LENGTH_SHORT).show();
        lastSelectedFragment = selectedFragment;
        return true;
    }

    private void RequestPermission() {
        if (ActivityCompat.checkSelfPermission(MainScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "true yes 2", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(MainScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        itemTheme = menu.findItem(R.id.itemTheme);
        itemTheme.setTitle(THEME_SELECTION);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemTheme:
                if (isWhiteTheme)
                    isWhiteTheme=false;
                else
                    isWhiteTheme=true;

                editor = appDataSaver.edit();
                editor.putBoolean("isWhiteTheme",isWhiteTheme);
                editor.apply();
                finish();
                startActivity(new Intent(MainScreen.this,MainScreen.class));
                return true;

            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}