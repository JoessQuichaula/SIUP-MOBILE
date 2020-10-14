package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.home_ui.FeedNews;
import com.example.myapplication.home_ui.Repartition;

public class TabNavPageAdapter extends FragmentStatePagerAdapter {


    public TabNavPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
               return new FeedNews();
            case 1:
                return new ServicesContainer();
            case 2:
                return new Repartition();
        }

        return new Fragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    private String[] tabTitles = new String[]{"Feed News", "Serviços", "Repartição"};

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
