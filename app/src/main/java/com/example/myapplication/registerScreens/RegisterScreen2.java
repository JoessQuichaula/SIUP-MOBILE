package com.example.myapplication.registerScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MessageService;
import com.example.myapplication.R;
import com.example.myapplication.SplashScreen;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import es.dmoral.toasty.Toasty;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterScreen2 extends AppCompatActivity {

    private Button btnBack;
    private Button btnNext;
    private ImageButton btnRestartCode;

    private String RESTART_CODE_TIME = "RESTART_CODE_TIME";
    private int restartCodeTime;
    private int countDownInterval = 1000; //MILLISECONDS
    private OtpTextView editNumberCode;
    private TextView txtReSendTimeCounter;
    private SharedPreferences appDataSaver;
    private SharedPreferences.Editor editor;
    private int userAttempts;

    private CountDownTimer countDownTimer;
    String messageCode;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen2);
        editNumberCode = findViewById(R.id.editNumberCode);
        btnRestartCode = findViewById(R.id.btnRestartCode);
        txtReSendTimeCounter = findViewById(R.id.txtReSendTimeCounter);
        btnBack = findViewById(R.id.btnBack2);
        btnNext = findViewById(R.id.btnNext2);

        appDataSaver = getSharedPreferences(SplashScreen.APP_SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        editor = appDataSaver.edit();

        if (getIntent().getStringExtra("messageCode")!= null){
            messageCode = getIntent().getStringExtra("messageCode");
        }

        phoneNumber = getIntent().getStringExtra("phoneNumber");

        userAttempts = appDataSaver.getInt("userAttempts",0);
        restartCodeTime = appDataSaver.getInt(RESTART_CODE_TIME,5000);
        btnRestartCode.setEnabled(false);

        countDownTimer = new CountDownTimer(restartCodeTime,countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int sec = (int)(millisUntilFinished/1000) % 60;
                int min = (int)(millisUntilFinished/1000) / 60;
                String formated = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
                txtReSendTimeCounter.setText(formated);
            }
            @Override
            public void onFinish() {
                txtReSendTimeCounter.setText("00:00");
                btnRestartCode.setEnabled(true);
            }
        }.start();
        btnRestartCode.setOnClickListener(v -> {
            if (userAttempts==5){
                Snackbar.make(findViewById(android.R.id.content),"Tente mais Tarde",Snackbar.LENGTH_LONG).show();
            }else {
                restartCodeTime *= 2;
                btnRestartCode.setEnabled(false);
                countDownTimer = new CountDownTimer(restartCodeTime,countDownInterval) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int sec = (int)(millisUntilFinished/1000) % 60;
                        int min = (int)(millisUntilFinished/1000) / 60;
                        String formated = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
                        txtReSendTimeCounter.setText(formated);
                    }

                    @Override
                    public void onFinish() {
                        btnRestartCode.setEnabled(true);
                    }
                }.start();
                messageCode = MessageService.sendMessageCode(RegisterScreen2.this,phoneNumber);

                editor.putInt("userAttempts",++userAttempts);
                editor.putInt(RESTART_CODE_TIME,restartCodeTime);
                editor.apply();
            }

        });

        nextBack();
    }

    private void nextBack(){
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen2.this, RegisterScreen.class);
            startActivity(intent);
        });
        btnNext.setOnClickListener(v -> {
            if (editNumberCode.getOTP().length()==6 && editNumberCode.getOTP().equals(messageCode)){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SplashScreen.APP_API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                Intent intent = new Intent(RegisterScreen2.this, RegisterScreen3.class);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);
            }
            else{
                editNumberCode.showError();
                Toasty.warning(RegisterScreen2.this,"Por Favor Preencha o código de verficação",Toasty.LENGTH_LONG).show();
            }

        });
    }
}