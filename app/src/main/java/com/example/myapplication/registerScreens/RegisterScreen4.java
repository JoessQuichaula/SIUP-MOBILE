package com.example.myapplication.registerScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.adapters.StadeAdapter;
import com.example.myapplication.models.CountyItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RegisterScreen4 extends AppCompatActivity {

    RecyclerView recyclerView;
    StadeAdapter stadeAdapter;
    FloatingActionButton btn_next;
    ArrayList<CountyItem> countyItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen4);

        /*
        countyItems = new ArrayList<>();
        countyItems.add(new CountyItem("LUANDA"));
        countyItems.add(new CountyItem("VIANA"));
        countyItems.add(new CountyItem("BELAS"));
        countyItems.add(new CountyItem("SAMBIZANGA"));
        countyItems.add(new CountyItem("VIANA"));
        countyItems.add(new CountyItem("TALATONA"));
        countyItems.add(new CountyItem("CAZENGA"));

        recyclerView = findViewById(R.id.recycle_stades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stadeAdapter = new StadeAdapter(countyItems);


        recyclerView.setAdapter(stadeAdapter);

        btn_next = findViewById(R.id.btn_next4);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register_Screen4.this, MainScreen.class);
                startActivity(intent);
            }
        });


         */

    }
}