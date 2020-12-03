package com.example.myapplication;

import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.models.ServiceItem;
import com.example.myapplication.models.UnityItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    //Attributes

    //WebSite URL Attribute
    public String baseUrl = "http://2a396c2f4173.ngrok.io";


    private ApiInterface apiInterface;
    Call<List<NewsItem>> callNews;
    Call<List<UnityItem>> callUnity;
    Call<List<ServiceItem>> callService;
    Call<List<DivisionItem>> callDivision;
    Call<DivisionTypeItem> callDivisionType;
    Call<CountyItem> callCounty;
    Call<List<DocumentItem>> callDocument;
    Call<ResponseBody> callUploadDocuments;


    //Call Support for API Consuming
    //Methods
    public void initRetrofit(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
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
    public Call<List<DocumentItem>> callDocument(int idService){
        callDocument = apiInterface.getDocumentItems(idService);
        return callDocument;
    }
    public Call<ResponseBody> callUploadDocuments(List<MultipartBody.Part> documentParts, RequestBody idDivision, RequestBody idService){
        callUploadDocuments = apiInterface.uploadDocumentFiles(documentParts,idDivision,idService);
        return callUploadDocuments;
    }

}
