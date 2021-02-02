package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AnnounceItem;

import java.util.List;

public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.AnnounceViewHolder> {

    private Context context;
    private List<AnnounceItem> announceItems;

    public AnnounceAdapter(Context context, List<AnnounceItem> announceItems) {
        this.context = context;
        this.announceItems = announceItems;
    }

    @NonNull
    @Override
    public AnnounceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_announce,parent,false);
        return new AnnounceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnounceViewHolder holder, int position) {
        AnnounceItem announceItem = announceItems.get(position);
        holder.imgAnnounce.setImageResource(announceItem.getImgAnnounce());

    }

    @Override
    public int getItemCount() {
        return announceItems.size();
    }

    class AnnounceViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAnnounce;

        public AnnounceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnnounce = itemView.findViewById(R.id.imgAnnounce);
        }
    }
}
