package com.example.myapplication.registerScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.SplashScreen;
import com.example.myapplication.R;

public class Register_Screen extends AppCompatActivity {


    EditText edit_number;
    Button btn_back;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen);

        edit_number = findViewById(R.id.edit_number);
        btn_back = findViewById(R.id.btnMore);
        btn_next = findViewById(R.id.btn_next);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen.this, SplashScreen.class);
                startActivity(intent);
                Toast.makeText(Register_Screen.this, v.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen.this, Register_Screen2.class);
                startActivity(intent);
            }
        });

        //TODO: LATER DO THIS -_-
/*
        edit_number.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (edit_number.getText().toString().length() == 3){
                    edit_number.setText(edit_number.getText().toString()+"-");
                    Toast.makeText(Register_Screen.this, "3", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });*/

    }

}