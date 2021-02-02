package com.example.myapplication.registerScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.LoginScreen;
import com.example.myapplication.MessageService;
import com.example.myapplication.SplashScreen;
import com.example.myapplication.R;

import es.dmoral.toasty.Toasty;


public class RegisterScreen extends AppCompatActivity {


    private EditText editPhoneNumber;

    private WebView webView;
    private int NUMBER_PHONE_REQUIRE_LENGTH = 9;
    private String REQUEST_TO_FILL_PHONE_NUMBER = "Por Favor Preencha o seu Número de Telemóvel";
    private TextView btnEnter;
    ImageView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        editPhoneNumber = findViewById(R.id.editPhoneNumber);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnNext = findViewById(R.id.btnNext);
        btnEnter = findViewById(R.id.btnEnter);
        appLogo = findViewById(R.id.app_logo);


        btnEnter.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(RegisterScreen.this,appLogo, ViewCompat.getTransitionName(appLogo));
            startActivity(intent,optionsCompat.toBundle());
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, SplashScreen.class);
            startActivity(intent);
        });

        btnNext.setOnClickListener(v -> {
            String phoneNumber = editPhoneNumber.getText().toString();
            if (phoneNumber.length() != NUMBER_PHONE_REQUIRE_LENGTH)
                Toasty.warning(RegisterScreen.this,REQUEST_TO_FILL_PHONE_NUMBER,Toasty.LENGTH_SHORT).show();
            else {
                String messageCode = MessageService.sendMessageCode(RegisterScreen.this,phoneNumber);
                Intent intent = new Intent(RegisterScreen.this, RegisterScreen2.class);
                intent.putExtra("messageCode",messageCode);
                intent.putExtra("phoneNumber",phoneNumber);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(RegisterScreen.this,appLogo, ViewCompat.getTransitionName(appLogo));
                startActivity(intent,optionsCompat.toBundle());
            }
        });

    }






}
