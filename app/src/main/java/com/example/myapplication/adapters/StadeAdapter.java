package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.CountyItem;

import java.util.ArrayList;

public class StadeAdapter {

   /*

    private ArrayList<CountyItem> countyItems;
    int last_position = 0;
    public StadeAdapter(ArrayList<CountyItem> countyItems) {
        this.countyItems = countyItems;
    }
    @NonNull
    @Override
    public StadeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stade,parent,false);
        return new StadeViewholder(view);
    }
    ImageView imageView;
    Boolean jafui;
    @Override
    public void onBindViewHolder(@NonNull final StadeViewholder holder, final int position) {
        CountyItem countyItem = countyItems.get(position);
        holder.txt_stade.setText(countyItem.getTxtCounty());

        if (jafui != null && jafui){
             imageView.setVisibility(View.INVISIBLE);

            holder.txt_stade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.img_check.setVisibility(View.VISIBLE);
                    imageView = holder.img_check;
                }
            });

        }else {
            holder.txt_stade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.img_check.setVisibility(View.VISIBLE);
                    jafui = true;
                    imageView = holder.img_check;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return countyItems.size();
    }
    public static class StadeViewholder extends RecyclerView.ViewHolder {

        private TextView txt_stade;
        private ImageView img_check;

        public StadeViewholder(@NonNull View itemView) {
            super(itemView);
            txt_stade = itemView.findViewById(R.id.txt_stade);
            img_check = itemView.findViewById(R.id.img_check);
        }
    }


    */
}

