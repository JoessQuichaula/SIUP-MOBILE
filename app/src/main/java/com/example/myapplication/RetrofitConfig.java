package com.example.myapplication;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.myapplication.models.ActivityItem;
import com.example.myapplication.models.ActivityStatusItem;
import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.models.ServiceItem;
import com.example.myapplication.models.UnityItem;
import com.example.myapplication.models.UserItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    //Attributes

    //WebSite URL Attribute
    public String baseUrl = SplashScreen.APP_API_BASE_URL;

    public boolean isFailureThreadListened = false;
    private Context context;
    public ArrayList<FragmentManager> fragmentManagers;

    private ApiInterface apiInterface;

    Call<UserItem> callUser;

    Call<List<NewsItem>> callNews;
    Call<List<UnityItem>> callUnity;

    Call<List<ServiceItem>> callServices;
    Call<List<ServiceItem>> callAllServices;

    Call<List<ActivityStatusItem>> callAllActivityStatus;

    Call<List<DivisionItem>> callDivisions;
    Call<List<DivisionItem>> callAllDivisions;

    Call<DivisionTypeItem> callDivisionType;
    Call<List<DivisionTypeItem>> callAllDivisionTypes;

    Call<CountyItem> callCounty;
    Call<List<CountyItem>> callAllCounties;

    Call<List<DocumentItem>> callDocument;
    Call<ResponseBody> callUploadDocuments;
    Call<ActivityItem> callActivity;
    private String authHeader;

    public RetrofitConfig(Context context,String authHeader) {
        this.context = context;
        this.authHeader = authHeader;
        fragmentManagers = new ArrayList<>();
    }

    //Call Support for API Consuming
    //Methods
    public void initRetrofit(){
        Gson gson = new GsonBuilder().serializeNulls().create();

        //http post - details
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


         apiInterface = retrofit.create(ApiInterface.class);

    }

    public Call<UserItem> getCallUser(){
        callUser = apiInterface.getUser(authHeader);
        return callUser;
    }

    public Call<List<NewsItem>> callNews(){
        callNews = apiInterface.getNewsItems(authHeader);
        return callNews;
    }
    public Call<List<UnityItem>> callUnity(){
        callUnity = apiInterface.getUnityItems(authHeader);
        return callUnity;
    }
    public Call<List<ServiceItem>> callServices(int idUnity){
        callServices = apiInterface.getServiceItems(idUnity,authHeader);
        return callServices;
    }


    public Call<List<ServiceItem>> getCallAllServices(){
        callAllServices = apiInterface.getAllServiceItems(authHeader);
        return callAllServices;
    }

    public Call<List<ActivityStatusItem>> getCallAllActivityStatus(){
        callAllActivityStatus = apiInterface.getAllActivityStatusItems(authHeader);
        return callAllActivityStatus;
    }

    public Call<List<DivisionItem>> callDivisions(int idUnity){
        callDivisions = apiInterface.getDivisionItems(idUnity,authHeader);
        return callDivisions;
    }

    public Call<List<DivisionItem>> getCallAllDivisions(){
        callAllDivisions = apiInterface.getAllDivisionItems(authHeader);
        return callAllDivisions;
    }

    public Call<DivisionTypeItem> callDivisionType(int idDivisionType){
        callDivisionType = apiInterface.getDivisionTypeItem(idDivisionType,authHeader);
        return callDivisionType;
    }

    public Call<List<DivisionTypeItem>> getCallAllDivisionTypes(){
        callAllDivisionTypes = apiInterface.getAllDivisionTypeItems(authHeader);
        return callAllDivisionTypes;
    }

    public Call<CountyItem> callCounty(int idCounty){
        callCounty = apiInterface.getDivisionCountyItem(idCounty,authHeader);
        return callCounty;
    }

    public Call<List<CountyItem>> getCallAllCounties(){
        callAllCounties = apiInterface.getAllDivisionCountyItems(authHeader);
        return callAllCounties;
    }

    public Call<List<DocumentItem>> callDocument(int idService){
        callDocument = apiInterface.getDocumentItems(idService,authHeader);
        return callDocument;
    }
    public Call<ResponseBody> callUploadDocuments(List<MultipartBody.Part> documentParts, RequestBody idDivision, RequestBody idService,RequestBody idUser){
        callUploadDocuments = apiInterface.uploadDocumentFiles(documentParts,idDivision,idService,idUser,authHeader);
        return callUploadDocuments;
    }

    public Call<ActivityItem> callActivity(int userId,int currentPage){
        callActivity = apiInterface.getActivitiesItems(userId,currentPage,authHeader);
        return callActivity;
    }


    public void failureThread(FragmentManager fragmentManager, int containerId){
        if (!isFailureThreadListened){
            //Toasty.custom(context,"Conexão com a Internet Falhou",R.drawable.reallogo2white, R.color.green,Toasty.LENGTH_LONG,true,true).show();
            Toasty.error(context,"Conexão com a Internet Falhou",Toasty.LENGTH_SHORT).show();
            isFailureThreadListened = true;
        }
        if (fragmentManager!=null){
            fragmentManager
                    .beginTransaction()
                    .replace(containerId,new FailureThread())
                    .addToBackStack(null)
                    .commit();
            fragmentManagers.add(fragmentManager);
        }


    }

    public void popBackAllFailedFragments(){
        for (FragmentManager fragmentManager: fragmentManagers){
            if(!fragmentManager.isDestroyed())
            fragmentManager.popBackStack();
        }
        fragmentManagers = new ArrayList<>();
    }

}
