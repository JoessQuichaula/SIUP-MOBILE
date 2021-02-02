package com.example.myapplication.bottom_ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.FeedNewsContainer;
import com.example.myapplication.R;
import com.example.myapplication.ServicesContainer;
import com.example.myapplication.TabNavPageAdapter;
import com.example.myapplication.home_ui.Repartition;
import com.example.myapplication.home_ui.services.ServiceScreen4;
import com.google.android.material.tabs.TabLayout;
import com.mr_sarsarabi.library.LockableViewPager;

import java.security.PublicKey;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public LockableViewPager tabNavBarhome_viewpager;
    public TabNavPageAdapter tabNavPageAdapter;
    public TabLayout home_navbar;
    public ArrayList<Fragment> fragments;

    //here its been used a third part library for LockSwipe
    public void LockViewPagerSwipe(){
        tabNavBarhome_viewpager.setSwipeLocked(true);
    }

    public Fragment getCurrentFragmentInViewPager(){
        return tabNavPageAdapter.getItem(tabNavBarhome_viewpager.getCurrentItem());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(new FeedNewsContainer());
        fragments.add(new ServicesContainer());
        fragments.add(new Repartition());



        home_navbar = view.findViewById(R.id.home_navbar);
        tabNavBarhome_viewpager = view.findViewById(R.id.tabNavbarhome_viewpager);
        tabNavBarhome_viewpager.setOffscreenPageLimit(2);
        tabNavPageAdapter = new TabNavPageAdapter(getFragmentManager(),fragments);
        tabNavBarhome_viewpager.setAdapter(tabNavPageAdapter);
        home_navbar.setupWithViewPager(tabNavBarhome_viewpager);
    }
}
