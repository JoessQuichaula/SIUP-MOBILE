package com.example.myapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.home_ui.FeedNews.FeedNews2;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.R;


import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

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
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feednews,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsItems.get(position);

        String txtNewsTitle = newsItem.getTxtNewsTitle();
        String txtNewsBody = newsItem.getTxtNewsBody();
        String imgNews = newsItem.getImgNews().replace("\\","/");
        String fullImagePath = baseUrl+"/storage/"+imgNews;

        Glide.with(context)
                .load(fullImagePath)
                .into(holder.imgNews);

        holder.txtNewsTitle.setText(Html.fromHtml(txtNewsTitle));
        holder.txtNewsBody.setText(Html.fromHtml(txtNewsBody));
        holder.txtNewsTime.setText(newsItem.getTxtNewsTime());
        holder.itemView.setOnClickListener(v -> {
            activity.findViewById(R.id.home_navbar).setVisibility(View.GONE);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.feedNewsContainer,new FeedNews2(txtNewsBody,txtNewsTitle))
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNews;
        TextView txtNewsTitle;
        TextView txtNewsBody;
        TextView txtNewsTime;
        TextView txtNewsReadMore;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            txtNewsTitle = itemView.findViewById(R.id.txtNewsTitle);
            txtNewsBody = itemView.findViewById(R.id.txtNewsBody);
            txtNewsTime = itemView.findViewById(R.id.txtNewsTime);
            txtNewsReadMore = itemView.findViewById(R.id.txtNewsReadMore);

        }
    }

}
