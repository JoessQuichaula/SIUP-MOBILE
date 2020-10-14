package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.registerScreens.Register_Screen;
import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class Test extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder()
                .setTitle("Encontre os Melhores Serviços")
                .setContent("Sed aliquet erat eget sem pharetra tempor. Nunc sed turpis vulputate, maximus odio non, ornare nulla. Curabitur interdum ultricies bibendum. Nunc id urna eget")
                .setBackgroundColor(Color.parseColor("#FD6721"))
                .setDrawable(R.drawable.onboarding1)
                .setSummary("Fique em Casa")
                .build());

        addFragment(new Step.Builder()
                .setTitle("Ache os Locais mais Próximos")
                .setContent("Nunc accumsan nulla neque, convallis pulvinar tellus lobortis quis. Praesent arcu sapien, egestas vitae malesuada eget, consectetur eu tortor. Vestibulum sed tortor")
                .setBackgroundColor(Color.parseColor("#e67027"))
                .setDrawable(R.drawable.onboarding2)
                .setSummary("Tudo Novo")
                .build());

        addFragment(new Step.Builder()
                .setTitle("Não Espere mais nas Filas")
                .setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed gravida odio mauris, ac commodo sem euismod sit amet. Donec tincidunt eget mi ut rhoncus. Aenean dignissim sem placerat, malesuada .")
                .setBackgroundColor(Color.parseColor("#FD9941"))
                .setDrawable(R.drawable.onboarding3)
                .setSummary("Vê novos resultados")
                .build());



    }

    @Override
    public void currentFragmentPosition(int position) {

    }
    @Override
    public void finishTutorial() {
        Intent intent = new Intent(this, Register_Screen.class);
        startActivity(intent);
    }
}
