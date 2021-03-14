package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.adapters.ActivitiesAdapter;
import com.example.myapplication.home_ui.FeedNews.FeedNews;
import com.example.myapplication.models.ActivityItem;
import com.example.myapplication.models.ActivityStatusItem;
import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.RequestItem;
import com.example.myapplication.models.ServiceItem;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivitiesScreen extends Fragment {


    int currentPage;
    int lastPage;
    Skeleton skeleton;
    List<RequestItem> requestItems;

    public ActivitiesScreen() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities_screen, container, false);
    }

    RecyclerView recyclerActivities;
    NestedScrollView nestedScrollView;
    ProgressBar activityProgressBar;

    ActivitiesAdapter activitiesAdapter;
    List<ServiceItem> allServiceItems;
    List<DivisionItem> allDivisionItems;
    List<CountyItem> allCountyItems;
    List<DivisionTypeItem> allDivisionTypeItems;
    List<ActivityStatusItem> allActivityStatusItems;
    RetrofitConfig retrofitConfig;
    MainScreen mainScreen;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerActivities = view.findViewById(R.id.recyclerActivities);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        activityProgressBar = view.findViewById(R.id.activityProgressBar);
        requestItems = new ArrayList<>();
        mainScreen = ((MainScreen)getActivity());
        mainScreen.appBarLayout.setElevation(6);

        recyclerActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitConfig = ((MainScreen)getActivity()).retrofitConfig;

        currentPage = 1;

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!nestedScrollView.canScrollVertically(1)){
                if (!skeleton.isSkeleton()) {
                    if (lastPage != currentPage){
                        ++currentPage;
                        retrofitFetch();
                        activitiesAdapter.notifyDataSetChanged();
                    }else {
                        activityProgressBar.setVisibility(View.INVISIBLE);
                    }
                 }
                }
            });

        skeleton =  SkeletonLayoutUtils.applySkeleton(recyclerActivities, R.layout.item_activities,7);
        skeleton.showSkeleton();

        getAllServiceItem();
        getAllCountyItems();
        getAllDivisionItems();
        getAllDivisionTypes();
        getAllActivityStatus();

        retrofitFetch();

    }
    private void retrofitFetch(){
        int userId = mainScreen.userItem.getId();
        Call<ActivityItem> call = retrofitConfig.callActivity(userId,currentPage);
        call.enqueue(new Callback<ActivityItem>() {
            @Override
            public void onResponse(Call<ActivityItem> call, Response<ActivityItem> response) {
                if (response.isSuccessful()){
                    onResponseSuccess(response.body(),retrofitConfig.baseUrl);
                }
                else{
                    retrofitConfig.failureThread(getFragmentManager(),R.id.activitiesContainer);
                }

            }
            @Override
            public void onFailure(Call<ActivityItem> call, @NonNull Throwable t) {
                if (getView()!=null){
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
                retrofitConfig.failureThread(getFragmentManager(),R.id.activitiesContainer);
            }
        });
    }
    private void onResponseSuccess(ActivityItem activityItems, String baseUrl){
        if (activityItems == null || allDivisionItems == null || allServiceItems == null
        || allCountyItems == null || allDivisionTypeItems == null || allActivityStatusItems == null){
            if (getView()!=null)
            Toast.makeText(getContext(),"Response is Successful but ResponseBody is null" , Toast.LENGTH_SHORT).show();
        }
        else{
            lastPage = activityItems.getLastPage();
            if (currentPage==lastPage){
                activityProgressBar.setVisibility(View.INVISIBLE);
            }
            for (RequestItem requestItem : activityItems.getRequestItems()){
                try {
                    String title = "";
                    for (ActivityStatusItem activityStatusItem: allActivityStatusItems){
                        if (activityStatusItem.getId() == requestItem.getActivityStatus()){
                            requestItem.setTxtActivityStatus(activityStatusItem.getStatus());
                        }
                    }
                    for (ServiceItem serviceItem : allServiceItems){
                        if (serviceItem.getIdService() == requestItem.getActivityService()){
                            title += serviceItem.getTxtService();
                            requestItem.setTxtActivityService(serviceItem.getTxtService());
                        }
                    }
                    for (DivisionItem divisionItem : allDivisionItems){
                        if (divisionItem.getId() == requestItem.getActivityDivision()){
                            title="";
                            requestItem.setTxtActivityDivisionAddress(divisionItem.getAddress());
                            for (CountyItem countyItem : allCountyItems){
                                if (countyItem.getIdCounty() == divisionItem.getIdType()){
                                    title += " "+countyItem.getTxtCounty();
                                    requestItem.setTxtActivityDivisionCounty(countyItem.getTxtCounty());
                                }
                            }

                            for (DivisionTypeItem divisionTypeItem : allDivisionTypeItems){
                                if (divisionTypeItem.getId() == divisionItem.getIdType()){
                                    title += " "+divisionTypeItem.getName();
                                    requestItem.setTxtActivityDivisionType(divisionTypeItem.getName());
                                }
                            }
                            title += " R:"+divisionItem.getId();
                            requestItem.setTxtActivityDivision(title);
                            break;
                        }
                    }

                }
                catch(NullPointerException ex){
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                }
            }
            requestItems.addAll(activityItems.getRequestItems());
            if (currentPage == 1 && getView()!=null){
                activitiesAdapter = new ActivitiesAdapter(getContext(),requestItems,getFragmentManager(),getResources());
                recyclerActivities.setAdapter(activitiesAdapter);
            }
        }
    }


    private void getAllServiceItem(){
        Call<List<ServiceItem>> call = retrofitConfig.getCallAllServices();
        call.enqueue(new Callback<List<ServiceItem>>() {
            @Override
            public void onResponse(Call<List<ServiceItem>> call, Response<List<ServiceItem>> response) {
                if (response.isSuccessful()){
                    allServiceItems = response.body();
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<ServiceItem>> call, Throwable t) {

            }
        });
    }
    private void getAllDivisionItems(){
        Call<List<DivisionItem>> call = retrofitConfig.getCallAllDivisions();
        call.enqueue(new Callback<List<DivisionItem>>() {
            @Override
            public void onResponse(Call<List<DivisionItem>> call, Response<List<DivisionItem>> response) {
                if (response.isSuccessful()){
                    allDivisionItems = response.body();

                }else{

                }
            }

            @Override
            public void onFailure(Call<List<DivisionItem>> call, Throwable t) {

            }
        });
    }
    private void getAllCountyItems(){
        Call<List<CountyItem>> call = retrofitConfig.getCallAllCounties();
        call.enqueue(new Callback<List<CountyItem>>() {
            @Override
            public void onResponse(Call<List<CountyItem>> call, Response<List<CountyItem>> response) {
                if (response.isSuccessful()){
                    allCountyItems = response.body();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<CountyItem>> call, Throwable t) {
            }
        });
    }
    private void getAllDivisionTypes(){
        Call<List<DivisionTypeItem>> call = retrofitConfig.getCallAllDivisionTypes();
        call.enqueue(new Callback<List<DivisionTypeItem>>() {
            @Override
            public void onResponse(Call<List<DivisionTypeItem>> call, Response<List<DivisionTypeItem>> response) {
                if (response.isSuccessful()){
                    allDivisionTypeItems = response.body();
                }else{

                }
            }

            @Override
            public void onFailure(Call<List<DivisionTypeItem>> call, Throwable t) {

            }
        });
    }
    private void getAllActivityStatus(){
        Call<List<ActivityStatusItem>> call = retrofitConfig.getCallAllActivityStatus();
        call.enqueue(new Callback<List<ActivityStatusItem>>() {
            @Override
            public void onResponse(Call<List<ActivityStatusItem>> call, Response<List<ActivityStatusItem>> response) {
                if (response.isSuccessful()){
                    allActivityStatusItems = response.body();
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<List<ActivityStatusItem>> call, Throwable t) {

            }
        });
    }


}