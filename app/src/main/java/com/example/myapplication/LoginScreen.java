package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.models.UserItem;
import com.example.myapplication.registerScreens.RegisterScreen;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity {

    EditText editPhoneNumber;
    EditText editPassword;
    Button btnEnter;
    TextView btnRegister;
    ImageView appLogo;
    AppBarLayout appBarLayout;
    CoordinatorLayout coordinatorLayout;
    AnimationDrawable animationDrawable;
    private SharedPreferences appDataSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        appDataSaver = getSharedPreferences(SplashScreen.APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        coordinatorLayout = findViewById(R.id.loginLayout);
        appBarLayout = findViewById(R.id.appbar);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editPassword = findViewById(R.id.editPassword);
        btnEnter=findViewById(R.id.btnEnter);
        btnRegister = findViewById(R.id.btnRegister);
        appLogo = findViewById(R.id.app_logo);
        animationDrawable = (AnimationDrawable) appBarLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginScreen.this,appLogo, ViewCompat.getTransitionName(appLogo));
            startActivity(intent,optionsCompat.toBundle());
        });

        btnEnter.setOnClickListener(v -> {
            String phoneNumber = editPhoneNumber.getText().toString();
            if (phoneNumber.length()>=9){
                login();
            }else
                Toasty.warning(LoginScreen.this,"Preencha o número de Telemóvel",Toasty.LENGTH_LONG).show();
        });
    }

    private void login(){
        String phoneNumber = editPhoneNumber.getText().toString();
        String password = editPassword.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SplashScreen.APP_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        String base = phoneNumber+":"+password;

        String authHeader = "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        Call<UserItem> call = apiInterface.getUser(authHeader);

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginScreen.this,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Por Favor Aguarde");
        progressDialog.setTitle("Carregando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    appDataSaver.edit().putBoolean("isUserLogged",true).apply();
                    appDataSaver.edit().putString("Auth",authHeader).apply();
                    Gson gson = new Gson();
                    String userJson = gson.toJson(response.body());
                    appDataSaver.edit().putString("user",userJson).apply();
                    Intent intent = new Intent(LoginScreen.this, MainScreen.class);
                    startActivity(intent);
                }
                else {
                    progressDialog.dismiss();
                    Toasty.error(LoginScreen.this,"Login Falhou",Toasty.LENGTH_SHORT).show();;
                }
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(LoginScreen.this,"Conexão com o servidor Falhou",Toasty.LENGTH_SHORT).show();
                Toast.makeText(LoginScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}