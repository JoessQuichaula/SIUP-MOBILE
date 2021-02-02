package com.example.myapplication;

import com.example.myapplication.models.ActivityItem;
import com.example.myapplication.models.ActivityStatusItem;
import com.example.myapplication.models.CountyItem;
import com.example.myapplication.models.DivisionItem;
import com.example.myapplication.models.DivisionTypeItem;
import com.example.myapplication.models.DocumentItem;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.models.UnityItem;
import com.example.myapplication.models.ServiceItem;
import com.example.myapplication.models.UserItem;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("api/users")
    Call<UserItem> getUser(@Header("Authorization") String authHeader);


    /*
    @POST("api/users")
    Call<ResponseBody> registerNewUser
            (@Part
             @Part("reparticao_id")RequestBody idDivision,
             @Part("servico_id") RequestBody idService,
             @Header("Authorization") String authHeader
            );
     */

    @GET("mimosms/v1/message/send")
    Call<String> getSendInfo(@Query("token") String token,
                           @Query("sender") String senderId,
                           @Query("recipients") String phoneNumber,
                           @Query("text") String messageCode);

    @GET("api/noticias")
    Call<List<NewsItem>> getNewsItems(@Header("Authorization") String authHeader);
    @GET("api/unidades")
    Call<List<UnityItem>> getUnityItems(@Header("Authorization") String authHeader);
    @GET("api/unidades/{id}/servicos")
    Call<List<ServiceItem>> getServiceItems(@Path("id") int idUnity,@Header("Authorization") String authHeader);
    @GET("api/unidades/{id}/reparticoes")
    Call<List<DivisionItem>> getDivisionItems(@Path("id")int idUnity,@Header("Authorization") String authHeader);
    @GET("api/tipo-reparticoes/{id}")
    Call<DivisionTypeItem> getDivisionTypeItem(@Path("id")int idDivisionType,@Header("Authorization") String authHeader);
    @GET("api/municipios/{id}")
    Call<CountyItem> getDivisionCountyItem(@Path("id")int idCounty,@Header("Authorization") String authHeader);
    @GET("api/servicos/{id}/documentos")
    Call<List<DocumentItem>> getDocumentItems(@Path("id")int idService,@Header("Authorization") String authHeader);


    @GET("api/demandas/{id}")
    Call<ActivityItem> getActivitiesItems(@Path("id")int userId,@Query("page") int currentPage,@Header("Authorization") String authHeader);

    @GET("api/estado-demandas")
    Call<List<ActivityStatusItem>> getAllActivityStatusItems(@Header("Authorization") String authHeader);

    @GET("api/servicos")
    Call<List<ServiceItem>> getAllServiceItems(@Header("Authorization") String authHeader);

    @GET("api/reparticoes")
    Call<List<DivisionItem>> getAllDivisionItems(@Header("Authorization") String authHeader);

    @GET("api/tipo-reparticoes")
    Call<List<DivisionTypeItem>> getAllDivisionTypeItems(@Header("Authorization") String authHeader);

    @GET("api/municipios")
    Call<List<CountyItem>> getAllDivisionCountyItems(@Header("Authorization") String authHeader);

    @Multipart
    @POST("api/demandas")
    Call<ResponseBody> uploadDocumentFiles
            (@Part List<MultipartBody.Part> documentItems,
             @Part("reparticao_id")RequestBody idDivision,
             @Part("servico_id") RequestBody idService,
             @Part("user_id") RequestBody idUser,
             @Header("Authorization") String authHeader
             );

    @Multipart
    @POST("api/users")
    Call<ResponseBody> createNewUser
            (@Part("name")RequestBody name,
             @Part("email") RequestBody email,
             @Part("telemovel")RequestBody phoneNumber,
             @Part("password") RequestBody password,
             @Part("bilhete_identidade") RequestBody BI,
             @Part MultipartBody.Part IdentityPassFile
            );


}
