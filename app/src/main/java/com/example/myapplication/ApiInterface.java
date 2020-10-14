package com.example.myapplication;

import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.models.UnityItem;
import com.example.myapplication.models.ServiceItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("api/noticias")
    Call<List<NewsItem>> getNewsItems();
    @GET("api/unidades")
    Call<List<UnityItem>> getUnityItems();
    @GET("api/unidades/{id}/servicos")
    Call<List<ServiceItem>> getServiceItems(@Path("id") int idUnity);
    @GET("api/unidades/{id}/reparticoes")
    Call<List<DivisionItem>> getDivisionItems(@Path("id")int idUnity);
    @GET("api/tipo-reparticoes/{id}")
    Call<DivisionTypeItem> getDivisionTypeItem(@Path("id")int idDivisionType);
    @GET("api/municipios/{id}")
    Call<CountyItem> getDivisionCountyItem(@Path("id")int idCounty);


}
