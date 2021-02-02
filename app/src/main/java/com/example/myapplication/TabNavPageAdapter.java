package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.home_ui.Repartition;

import java.util.ArrayList;

public class TabNavPageAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    public TabNavPageAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
               return fragments.get(position);
            case 1:
                return fragments.get(position);
            case 2:
                return fragments.get(position);
        }

        return new Fragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private String[] tabTitles = new String[]{"Feed News", "Serviços", "Repartição"};

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
