package com.example.myapplication.registerScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class Register_Screen2 extends AppCompatActivity {

    Button btn_back;
    Button btn_next;
    EditText editNumberCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen2);

        editNumberCode = findViewById(R.id.edit_numberCode);

        editNumberCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                s.append("-");
                Toast.makeText(Register_Screen2.this, s.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_back = findViewById(R.id.btnMore);
        btn_next = findViewById(R.id.btn_next);
        next_back();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(this, "yeh", Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);
    }

    private void next_back(){

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen2.this, Register_Screen.class);
                startActivity(intent);
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen2.this,Register_Screen3.class);
                startActivity(intent);
            }
        });
    }
}