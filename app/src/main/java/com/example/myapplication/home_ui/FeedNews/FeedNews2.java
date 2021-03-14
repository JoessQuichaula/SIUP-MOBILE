package com.example.myapplication.home_ui.FeedNews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.anggastudio.dynamicimagegetter.DynamicImageGetter;
import com.bumptech.glide.Glide;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.example.myapplication.models.NewsItem;
import com.faltenreich.skeletonlayout.Skeleton;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;

public class FeedNews2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feednews2, container, false);
    }

    String newsBody;
    CharSequence newsTitle;
    public FeedNews2(String newsBody,CharSequence newsTitle){
        this.newsBody = newsBody;
        this.newsTitle = newsTitle;
    }

    TextView txtNewsBody;
    Skeleton skeleton;
    NestedScrollView nestedScrollView;
    MainScreen mainScreen;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainScreen = ((MainScreen)getActivity());
        mainScreen.appBarLayout.setExpanded(true);
        mainScreen.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainScreen.getSupportActionBar().setDisplayShowHomeEnabled(true);
        mainScreen.toolbar.setTitle(newsTitle);
        mainScreen.appBarLayout.setElevation(6);
        mainScreen.appLogo.setVisibility(View.GONE);
        nestedScrollView = view.findViewById(R.id.scrollLayout);

        txtNewsBody = view.findViewById(R.id.txtNewsBody);
        skeleton = view.findViewById(R.id.skeleton);
        skeleton.showSkeleton();

        if (getView()!=null){
            DynamicImageGetter.with(getContext())
                    .load(newsBody)
                    .mode(DynamicImageGetter.FULL_WIDTH)
                    .into(txtNewsBody);
            skeleton.showOriginal();
        }

        HomeFragment homeFragment =
                (HomeFragment)getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag("homeFragment");


        homeFragment.LockViewPagerSwipe();

    }


}
