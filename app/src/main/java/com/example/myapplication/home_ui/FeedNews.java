package com.example.myapplication.home_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.R;
import com.example.myapplication.adapters.NewsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedNews extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feednews,container,false);
    }

    RecyclerView recycler_news;
    NewsAdapter newsAdapter;
    ImageView imgMainNews;
    TextView txtMainNewsTitle;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_news = view.findViewById(R.id.recycler_news);
        imgMainNews = view.findViewById(R.id.imgMainNews);
        txtMainNewsTitle = view.findViewById(R.id.txtMainNewsTitle);

        retrofitFetch();
        recycler_news.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void retrofitFetch(){
        final RetrofitConfig retrofitConfig = new RetrofitConfig();
        retrofitConfig.initRetrofit();
        Call<List<NewsItem>> call = retrofitConfig.callNews();
        call.enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Call<List<NewsItem>> call, Response<List<NewsItem>> response) {
                if (response.isSuccessful()){
                    onResponseSuccess(response.body(),retrofitConfig.baseUrl);
                }
                else
                    Toast.makeText(getContext(), "Response is not Sucessful", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<NewsItem>> call, Throwable t) {
                Toast.makeText(getContext(), "Internet Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onResponseSuccess(List<NewsItem> newsItems,String baseUrl){
        if (newsItems == null)
            Toast.makeText(getContext(),"Response is Successful but ResponseBody is null" , Toast.LENGTH_SHORT).show();
        else{
            txtMainNewsTitle.setText(newsItems.get(0).getTxtNewsTitle());
            Glide.with(getContext())
                    .load(baseUrl+"/storage/"+ newsItems.get(0).getImgNews())
                    .into(imgMainNews);
            newsAdapter = new NewsAdapter(newsItems,getContext(),baseUrl);
            recycler_news.setAdapter(newsAdapter);
        }
    }


}
