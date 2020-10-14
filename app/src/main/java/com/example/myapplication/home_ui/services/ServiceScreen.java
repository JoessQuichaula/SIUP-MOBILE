package com.example.myapplication.home_ui.services;

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
import com.example.myapplication.adapters.UnityAdapter;
import com.example.myapplication.models.ServiceItem;
import com.example.myapplication.adapters.ServiceAdapter;
import com.example.myapplication.models.UnityItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceScreen extends Fragment {

    int idUnity;

    public ServiceScreen(int idUnity) {
        this.idUnity = idUnity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service_screen2, container, false);
    }

    RecyclerView recyclerService;
    ServiceAdapter serviceAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerService = view.findViewById(R.id.recycler_subService);
        recyclerService.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitFetch();
    }

    private void retrofitFetch() {
        final RetrofitConfig retrofitConfig = new RetrofitConfig();
        retrofitConfig.initRetrofit();
        Call<List<ServiceItem>> call = retrofitConfig.callService(idUnity);
        call.enqueue(new Callback<List<ServiceItem>>() {
            @Override
            public void onResponse(Call<List<ServiceItem>> call, Response<List<ServiceItem>> response) {
                if (response.isSuccessful()) {
                    onResponseSuccess(response.body(), retrofitConfig.baseUrl);
                } else
                    Toast.makeText(getContext(), "Response is not Sucessful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<ServiceItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onResponseSuccess(List<ServiceItem> serviceItems, String baseUrl) {
        if (serviceItems == null)
            Toast.makeText(getContext(), "Response is Successful but ResponseBody is null", Toast.LENGTH_SHORT).show();
        else {
            serviceAdapter = new ServiceAdapter(serviceItems, getContext(), getFragmentManager(), getActivity(), baseUrl,idUnity);
            recyclerService.setAdapter(serviceAdapter);
        }
    }
}