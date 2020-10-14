package com.example.myapplication;

import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.models.ServiceItem;
import com.example.myapplication.models.UnityItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public String baseUrl = "http://1967dc5232ee.ngrok.io";
    private ApiInterface apiInterface;
    Call<List<NewsItem>> callNews;
    Call<List<UnityItem>> callUnity;
    Call<List<ServiceItem>> callService;
    Call<List<DivisionItem>> callDivision;
    Call<DivisionTypeItem> callDivisionType;
    Call<CountyItem> callCounty;

    public void initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         apiInterface = retrofit.create(ApiInterface.class);
    }

    public Call<List<NewsItem>> callNews(){
        callNews = apiInterface.getNewsItems();
        return callNews;
    }

    public Call<List<UnityItem>> callUnity(){
        callUnity = apiInterface.getUnityItems();
        return callUnity;
    }

    public Call<List<ServiceItem>> callService(int idUnity){
        callService = apiInterface.getServiceItems(idUnity);
        return callService;
    }

    public Call<List<DivisionItem>> callDivision(int idUnity){
        callDivision = apiInterface.getDivisionItems(idUnity);
        return callDivision;
    }

    public Call<DivisionTypeItem> callDivisionType(int idDivisionType){
        callDivisionType = apiInterface.getDivisionTypeItem(idDivisionType);
        return callDivisionType;
    }

    public Call<CountyItem> callCounty(int idCounty){
        callCounty = apiInterface.getDivisionCountyItem(idCounty);
        return callCounty;
    }

}
