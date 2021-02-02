package com.example.myapplication.adapters;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.myapplication.home_ui.FeedNews.FeedNews2;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.R;


import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.News_ViewHolder> {

    private List<NewsItem> newsItems;
    private Context context;
    private String baseUrl;
    private FragmentManager fragmentManager;
    private Activity activity;
    public NewsAdapter(Activity activity,List<NewsItem> newsItems, Context context, String baseUrl, FragmentManager fragmentManager) {
        this.activity = activity;
        this.newsItems = newsItems;
        this.context = context;
        this.baseUrl = baseUrl;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public News_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feednews,parent,false);
        return new News_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_ViewHolder holder, int position) {



        NewsItem newsItem = newsItems.get(position);

        String txtNewsTitle = newsItem.getTxtNewsTitle();
        String txtNewsBody = newsItem.getTxtNewsBody();
        String imgNews = newsItem.getImgNews().replace("\\","/");
        String fullImagePath = baseUrl+"/storage/"+imgNews;

        Glide.with(context)
                .load("https://jornaldeangola.ao"+newsItem.getImgNews())
                .into(holder.imgNews);


        holder.txtNewsTitle.setText(Html.fromHtml(txtNewsTitle));
        holder.txtNewsBody.setText(Html.fromHtml(txtNewsBody));
        holder.txtNewsTime.setText(newsItem.getTxtNewsTime());

        holder.itemView.setOnClickListener(v -> {
            activity.findViewById(R.id.appbar).setElevation(6);
            activity.findViewById(R.id.app_logo).setVisibility(View.GONE);
            activity.findViewById(R.id.home_navbar).setVisibility(View.GONE);
            FeedNews2 feedNews2 = new FeedNews2();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("txtNewsTitle",holder.txtNewsTitle.getText());
            bundle.putString("txtNewsBody",txtNewsBody);
            bundle.putString("imgNews","https://jornaldeangola.ao"+newsItem.getImgNews());
            bundle.putString("newsLink",newsItem.getNewsLink());
            feedNews2.setArguments(bundle);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.feedNewsContainer,feedNews2)
                    .addToBackStack(null)
                    .commit();
        });


    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public static class News_ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView txtNewsTitle;
        TextView txtNewsBody;
        TextView txtNewsTime;
        TextView txtNewsReadMore;


        public News_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            txtNewsTitle = itemView.findViewById(R.id.txtNewsTitle);
            txtNewsBody = itemView.findViewById(R.id.txtNewsBody);
            txtNewsTime = itemView.findViewById(R.id.txtNewsTime);
            txtNewsReadMore = itemView.findViewById(R.id.txtNewsReadMore);

        }
    }

}
