package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.models.IntroductionItem;

import java.util.List;

public class IntroductionPagerAdapter extends PagerAdapter {

    private Context context;
    private List<IntroductionItem> introductionItems;

    public IntroductionPagerAdapter(Context context, List<IntroductionItem> introductionItems) {
        this.context = context;
        this.introductionItems = introductionItems;
    }

    @Override
    public int getCount() {
        return introductionItems.size();
    }
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;


    //animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
       // animationDrawable.setEnterFadeDuration(2000);
        //animationDrawable.setExitFadeDuration(4000);
        //animationDrawable.start();

    Animation animation;
    ImageView apresentationImg;
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layoutScreen = inflater.inflate(R.layout.item_apresentation,null);
        ImageView apresentationImg = layoutScreen.findViewById(R.id.apresentationImg);
        TextView apresentationTitle = layoutScreen.findViewById(R.id.apresentationTitle);
        TextView apresentationBody = layoutScreen.findViewById(R.id.apresentationBody);
        apresentationImg.setImageResource(introductionItems.get(position).getApresentationImg());
        apresentationTitle.setText(introductionItems.get(position).getApresentationTitle());
        apresentationBody.setText(introductionItems.get(position).getApresentationBody());

        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
