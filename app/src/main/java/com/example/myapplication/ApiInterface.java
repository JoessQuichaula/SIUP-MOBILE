package com.example.myapplication;

import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.models.UnityItem;
import com.example.myapplication.models.ServiceItem;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @GET("api/servicos/{id}/documentos")
    Call<List<DocumentItem>> getDocumentItems(@Path("id")int idService);


    @Multipart
    @POST("api/demandas")
    Call<ResponseBody> uploadDocumentFiles
            (@Part List<MultipartBody.Part> documentsUri,
             @Part("reparticao_id")RequestBody idDivision,
             @Part("servico_id") RequestBody idService
             );

}
