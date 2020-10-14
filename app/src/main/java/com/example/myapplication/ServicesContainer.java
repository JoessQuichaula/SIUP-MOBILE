package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.home_ui.Unity;


public class ServicesContainer extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_container, container, false);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.services_container,new Unity(),null)
                .addToBackStack(null)
                .commit();


        return  view;
    }

}