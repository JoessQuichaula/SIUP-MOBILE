package com.example.myapplication.home_ui.FeedNews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.FeedNewsContainer;
import com.example.myapplication.MainScreen;
import com.example.myapplication.RetrofitConfig;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.R;
import com.example.myapplication.adapters.NewsAdapter;
import com.faltenreich.skeletonlayout.SkeletonLayout;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
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
    NewsItem newsItemForMainNews;
    RecyclerView recyclerNews;
    NewsAdapter newsAdapter;
    ImageView imgMainNews;
    TextView txtMainNewsTitle;
    SwipeRefreshLayout swipeRefreshLayout;
    CardView mainNews;
    Skeleton skeleton;
    Skeleton skeletonForMainNews;
    NestedScrollView nestedScrollView;
    int newsPager = 1;
    MainScreen mainScreen;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainScreen = ((MainScreen)getActivity());
        recyclerNews = view.findViewById(R.id.recycler_news);
        imgMainNews = view.findViewById(R.id.imgMainNews);
        txtMainNewsTitle = view.findViewById(R.id.txtMainNewsTitle);
        mainNews = view.findViewById(R.id.mainNews);
        nestedScrollView = view.findViewById(R.id.scrollLayout);

        /*
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!nestedScrollView.canScrollVertically(1)){
                if (!skeleton.isSkeleton()){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ++newsPager;
                            newsAdapter.notifyDataSetChanged();
                        }
                    },1000);
                }
            }
        });

         */

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            skeleton.showSkeleton();
            skeletonForMainNews.showSkeleton();
           retrofitFetch();
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   swipeRefreshLayout.setRefreshing(false);
               }
           },1000);

        });
        mainNews.setOnClickListener(v -> {
            mainScreen.appLogo.setVisibility(View.GONE);
            getActivity().findViewById(R.id.home_navbar).setVisibility(View.GONE);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.feedNewsContainer,new FeedNews2(newsItemForMainNews.getTxtNewsBody(),newsItemForMainNews.getTxtNewsTitle()))
                    .addToBackStack(null)
                    .commit();
        });

        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerNews.setHasFixedSize(true);
        recyclerNews.setNestedScrollingEnabled(true);

        retrofitFetch();

        skeleton = SkeletonLayoutUtils.applySkeleton(recyclerNews, R.layout.item_feednews);
        skeletonForMainNews = view
                 .findViewById(R.id.skeletonLayoutForMainNews);

        skeleton.showSkeleton();
        skeletonForMainNews.showSkeleton();


    }

    private void retrofitFetch(){
        RetrofitConfig retrofitConfig = mainScreen.retrofitConfig;
        Call<List<NewsItem>> call = retrofitConfig.callNews();
        call.enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Call<List<NewsItem>> call, Response<List<NewsItem>> response) {
                if (response.isSuccessful()){
                    onResponseSuccess(response.body(),retrofitConfig.baseUrl);
                }
                else{
                    retrofitConfig.failureThread(getFragmentManager(),R.id.feedNewsContainer);
                }
            }
            @Override
            public void onFailure(Call<List<NewsItem>> call, Throwable t) {
                retrofitConfig.failureThread(getFragmentManager(),R.id.feedNewsContainer);
            }
        });
    }
    private void onResponseSuccess(List<NewsItem> newsItems,String baseUrl){
        newsItemForMainNews = newsItems.get(0);
        txtMainNewsTitle.setText(newsItemForMainNews.getTxtNewsTitle());
        if (isAdded()){
            String imgNews = newsItemForMainNews.getImgNews().replace("\\","/");
            String fullImagePath = baseUrl+"/storage/"+imgNews;
            Glide.with(getContext())
                    .load(fullImagePath)
                    .into(imgMainNews);
        }
        skeletonForMainNews.showOriginal();
        newsAdapter = new NewsAdapter(getActivity(),newsItems,getContext(),baseUrl,getFragmentManager());
        recyclerNews.setAdapter(newsAdapter);
    }

    /*
    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String title ="";
            String imgNews;
            String newsTitle;
            String newsBody;
            String newsTime="";
            String newsLink="";
            Document doc;
            try {
                doc = Jsoup.connect("https://jornaldeangola.ao/ao/noticias/index.php?tipo=1&idSec=18/pagina-"+newsPager+"/").get();
                Elements elements = doc.getElementsByClass("item-noticia");
                for (Element element : elements){
                    imgNews = element.getElementsByTag("a").get(0).getElementsByClass("capa").get(0).attr("style");
                    int first = imgNews.indexOf("/");
                    int last = imgNews.lastIndexOf(")");
                    imgNews = imgNews.substring(first,last);
                    newsTitle = element.getElementsByTag("a").get(0).getElementsByTag("h2").text();
                    newsBody = element.getElementsByTag("a").get(0).getElementsByTag("p").text();
                    int i = 0;
                    for (Element element1 : element.getElementsByTag("a").get(0).getElementsByClass("data").get(0).getElementsByTag("span")){
                        if (i==0)
                        newsTime += element1.text()+"\n";
                        else if (i==1)
                        newsTime += element1.text();
                        ++i;
                    }
                    newsLink = element.getElementsByTag("a").attr("href");
                    newsItemsForPro.add(new NewsItem(imgNews,newsTitle,newsBody,newsTime,newsLink));
                    newsTime = "";
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
     */
}
