package com.example.myapplication.home_ui.services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.bottom_ui.HomeFragment;


public class ServiceScreen2 extends Fragment {

    private Button btnRequest;
    private TextView txtSubService;
    private TextView txtDesc;
    private ImageView imgService;
    Button btnProcedures;
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
        if (getActivity().findViewById(R.id.appbar).getVisibility() != View.VISIBLE
                && getActivity().findViewById(R.id.home_navbar).getVisibility() != View.VISIBLE){
            getActivity().findViewById(R.id.appbar).setVisibility(View.VISIBLE);

        }
        ((MainScreen) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainScreen) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainScreen) getActivity()).appBarLayout.setElevation(6);

        txtSubService = view.findViewById(R.id.txtSubService);
        txtDesc =  view.findViewById(R.id.txtDescService);
        btnProcedures = view.findViewById(R.id.btn_procedures);

        txtSubService.setText(getArguments().getCharSequence("txtService"));
        txtDesc.setText(getArguments().getCharSequence("txtDesc"));

        btnRequest = view.findViewById(R.id.btn_request);
        imgService = view.findViewById(R.id.imgService);
        Glide.with(getContext())
                .load(getArguments().getString("FullImgPath"))
                .into(imgService);

        btnProcedures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.servicos.minjusdh.gov.ao/outros-servicos-ao-cidadao/32/identificacao-civil-e-criminal"));
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeFragment homeFragment =
                (HomeFragment)getActivity()
                        .getSupportFragmentManager()
                        .findFragmentByTag("homeFragment");
        homeFragment.LockViewPagerSwipe();

        btnRequest.setOnClickListener(v -> {
            ((MainScreen) getActivity()).appBarLayout.setVisibility(View.GONE);
            ((MainScreen) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((MainScreen) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
            getFragmentManager().beginTransaction()
                    .replace(R.id.services_container,new ServiceScreen3(idUnity,idService))
                    .addToBackStack(null)
                    .commit();
        });
    }
}