package com.example.myapplication.home_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.models.UnityItem;
import com.example.myapplication.adapters.UnityAdapter;

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
    UnityAdapter unityAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_service = view.findViewById(R.id.recycler_service);
        recycler_service.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitFetch();

    }
    private void retrofitFetch(){
        final RetrofitConfig retrofitConfig = new RetrofitConfig();
        retrofitConfig.initRetrofit();
        Call<List<UnityItem>> call = retrofitConfig.callUnity();
        call.enqueue(new Callback<List<UnityItem>>() {
            @Override
            public void onResponse(Call<List<UnityItem>> call, Response<List<UnityItem>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Funcionou nas Unidades", Toast.LENGTH_SHORT).show();
                    onResponseSuccess(response.body(),retrofitConfig.baseUrl);
                }
                else
                    Toast.makeText(getContext(), "Response is not Sucessful", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<UnityItem>> call, @NonNull Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
