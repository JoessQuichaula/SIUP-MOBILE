package com.example.myapplication.home_ui.services;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.SliderAdapter;
import com.example.myapplication.adapters.AnnounceAdapter;
import com.example.myapplication.models.AnnounceItem;
import com.example.myapplication.models.UnityItem;
import com.example.myapplication.adapters.UnityAdapter;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.faltenreich.skeletonlayout.Skeleton;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Unity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unity,container,false);
    }

    RecyclerView recycler_service;
    RecyclerView recyclerAnnounce;
    UnityAdapter unityAdapter;
    Skeleton skeleton;
    List<AnnounceItem> announceItems;
    AnnounceAdapter announceAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        SliderView sliderView = view.findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapter());
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

         */
        announceItems = new ArrayList<>();
        announceItems.add(new AnnounceItem(R.drawable.announce));
        announceItems.add(new AnnounceItem(R.drawable.announce2));
        announceItems.add(new AnnounceItem(R.drawable.announce3));
        announceItems.add(new AnnounceItem(R.drawable.announce4));
        announceItems.add(new AnnounceItem(R.drawable.announce5));

        announceAdapter = new AnnounceAdapter(getContext(),announceItems);

        recyclerAnnounce = view.findViewById(R.id.recyclerAnnounce);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerAnnounce.setLayoutManager(linearLayoutManager);
        recyclerAnnounce.setAdapter(announceAdapter);
        recycler_service = view.findViewById(R.id.recycler_service);
        recycler_service.setLayoutManager(new LinearLayoutManager(getContext()));
        skeleton = SkeletonLayoutUtils.applySkeleton(recycler_service, R.layout.item_unity);
        skeleton.showSkeleton();
        retrofitFetch();

    }
    private void retrofitFetch(){
        RetrofitConfig retrofitConfig = ((MainScreen)getActivity()).retrofitConfig;
        Call<List<UnityItem>> call = retrofitConfig.callUnity();
        call.enqueue(new Callback<List<UnityItem>>() {
            @Override
            public void onResponse(Call<List<UnityItem>> call, Response<List<UnityItem>> response) {
                if (response.isSuccessful()){
                    onResponseSuccess(response.body(),retrofitConfig.baseUrl);
                }
                else{
                    retrofitConfig.failureThread(getFragmentManager(),R.id.services_container);
                }

            }
            @Override
            public void onFailure(Call<List<UnityItem>> call, @NonNull Throwable t) {
                retrofitConfig.failureThread(getFragmentManager(),R.id.services_container);
            }
        });
    }
    private void onResponseSuccess(List<UnityItem> unityItems, String baseUrl){
        if (unityItems == null)
            Toast.makeText(getContext(),"Response is Successful but ResponseBody is null" , Toast.LENGTH_SHORT).show();
        else{
            unityAdapter = new UnityAdapter(unityItems,getContext(),getFragmentManager(),baseUrl);
            recycler_service.setAdapter(unityAdapter);
        }
    }

}
