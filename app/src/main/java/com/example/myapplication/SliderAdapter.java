package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterViewHolder>{


    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announce, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, int position) {
        switch (position){
            case 0:
                viewHolder.img1.setImageResource(R.drawable.siup_without_logo_dark);
                break;
            case 1:
                viewHolder.img1.setImageResource(R.drawable.siup_without_logo);
                break;
            case 2:
                viewHolder.img1.setImageResource(R.drawable.falselogo2);
                break;
            default:
                viewHolder.img1.setImageResource(R.drawable.falselogo);
                break;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView img1;
        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            img1=itemView.findViewById(R.id.imgAnnounce);
        }
    }

}
