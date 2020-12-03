package com.example.myapplication.home_ui.services;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;


public class ServiceScreen2 extends Fragment {

    private Button btnRequest;
    TextView txtSubService;
    TextView txtDesc;

    int idUnity;
    int idService;

    public ServiceScreen2(int idUnity,int idService) {
        this.idUnity = idUnity;
        this.idService = idService;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service_screen2, container, false);
        txtSubService = view.findViewById(R.id.txtSubService);
        txtDesc =  view.findViewById(R.id.txtDescService);
        txtSubService.setText(getArguments().getString("txtSubService"));
        txtDesc.setText(getArguments().getString("txtDesc"));
        btnRequest = view.findViewById(R.id.btn_request);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.services_container,new ServiceScreen3(idUnity,idService))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}