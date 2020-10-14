package com.example.myapplication.bottom_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.TabNavPageAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    public ViewPager tabNavBarhome_viewpager;
    TabNavPageAdapter tabNavPageAdapter;
    public TabLayout home_navbar;
    private FragmentManager homeFragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        home_navbar = view.findViewById(R.id.home_navbar);
        tabNavBarhome_viewpager = view.findViewById(R.id.tabNavbarhome_viewpager);
        tabNavPageAdapter = new TabNavPageAdapter(getFragmentManager());
        tabNavBarhome_viewpager.setAdapter(tabNavPageAdapter);

        homeFragmentManager = getFragmentManager();
        home_navbar.setupWithViewPager(tabNavBarhome_viewpager);

        return view;
    }



}
