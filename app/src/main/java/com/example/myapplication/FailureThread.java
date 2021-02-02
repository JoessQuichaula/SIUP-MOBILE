package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


public class FailureThread extends Fragment {


    public FailureThread() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_failure_thread, container, false);
    }



    ImageView btnReset;
    boolean isShown = false;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnReset = view.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager cm =
                        (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if (isConnected){
                    ((MainScreen)getActivity()).retrofitConfig.popBackAllFailedFragments();
                    ((MainScreen)getActivity()).retrofitConfig.isFailureThreadListened=false;

                }else {
                    if (!isShown){
                        isShown = true;
                        Toasty.error(getActivity(),"Conexão com a Internet Falhou",Toasty.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isShown = false;
                            }
                        },4000);
                    }
                }
            }
        });
    }
}