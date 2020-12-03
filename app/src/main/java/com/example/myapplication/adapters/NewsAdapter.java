package com.example.myapplication.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.NewsItem;
import com.example.myapplication.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.News_ViewHolder> {

    private List<NewsItem> newsItems;
    private Context context;
    private String baseUrl;
    public NewsAdapter(List<NewsItem> newsItems, Context context,String baseUrl) {
        this.newsItems = newsItems;
        this.context = context;
        this.baseUrl = baseUrl;
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
        String imgNews = newsItem.getImgNews().replace("\\","/");;
        Glide.with(context)
                .load(baseUrl+"/storage/"+imgNews)
                .into(holder.imgNews);
        holder.txtNewsTitle.setText(Html.fromHtml(txtNewsTitle));
        holder.txtNewsBody.setText(Html.fromHtml(txtNewsBody));


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

        public News_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            txtNewsTitle = itemView.findViewById(R.id.txtNewsTitle);
            txtNewsBody = itemView.findViewById(R.id.txtNewsBody);
            txtNewsTime = itemView.findViewById(R.id.txtNewsTime);

        }
    }

}
