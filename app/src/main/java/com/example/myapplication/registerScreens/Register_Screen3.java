package com.example.myapplication.registerScreens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;

public class Register_Screen3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edit_email;
    EditText edit_name;
    Button btn_back;
    Button btn_next;
    Spinner spinner;
    View horizontal_line;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen3);

        edit_email = findViewById(R.id.edit_email);
        edit_name = findViewById(R.id.edit_name);
        horizontal_line = findViewById(R.id.horizontal_line);
        btn_back = findViewById(R.id.btnMore);
        btn_next = findViewById(R.id.btn_next);


        spinner = findViewById(R.id.spin_stades);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.provincias,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        focusChanges();
        next_back();

    }

    private void next_back(){

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen3.this,Register_Screen2.class);
                startActivity(intent);
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen3.this,Register_Screen4.class);
                startActivity(intent);
            }
        });
    }
    private void focusChanges(){
        edit_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    edit_email.setBackgroundTintList(getResources().getColorStateList(R.color.colorGradient1));
                else
                    edit_email.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
                Toast.makeText(Register_Screen3.this, "focus", Toast.LENGTH_SHORT).show();
            }
        });

        spinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    horizontal_line.setBackgroundColor(getResources().getColor(R.color.colorGradient2));

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}