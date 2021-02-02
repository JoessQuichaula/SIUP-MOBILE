package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.registerScreens.RegisterScreen;
import com.example.myapplication.registerScreens.RegisterScreen2;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageService {

    private static boolean isShown = false;

    public static String sendMessageCode(Context context,String phoneNumber){

        //CONFIG TO KNOW IF PHONE IS CONNECTED TO WIFI OR DATA
        String VERIFY_INTERNET_REQUEST = "Por Favor Verifique a sua Conexão com a Internet";
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork =
                cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();



        //Verify if phone is connected to wifi or data
        if (isConnected){
            return send(phoneNumber,context);
        }
        else {
            if (!isShown){
                isShown = true;
                Toasty.error(context,VERIFY_INTERNET_REQUEST,Toasty.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isShown = false;
                    }
                },4000);
            }
        }
        return null;
    }

    private static String send(String phoneNumber, Context context){
        StringBuilder messageCode= new StringBuilder();
        for (int i = 0; i < 6; ++i){
            messageCode.append((int)(Math.random() * 10));
        }

        String BASE_URL = "http://52.30.114.86:8080/";
        String TOKEN = "1b6a6ebb69565f06b3df06d1badbe3f4945623998";
        String SENDER_ID = "SIUP";
        String MESSAGE = "Caro Utente, O seu código de Confirmação é: "+messageCode+"\nNão Compartilhe com terceiros \nSistema Integrado das Unidades Públicas";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<String> call = apiInterface.getSendInfo(TOKEN,SENDER_ID,phoneNumber,MESSAGE);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context,R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Por Favor Aguarde");
        progressDialog.setTitle("Carregando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //empty Field
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

        return messageCode.toString();
    }

}
