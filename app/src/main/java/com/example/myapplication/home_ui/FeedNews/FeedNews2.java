package com.example.myapplication.home_ui.FeedNews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.anggastudio.dynamicimagegetter.DynamicImageGetter;
import com.bumptech.glide.Glide;
import com.example.myapplication.MainScreen;
import com.example.myapplication.R;
import com.example.myapplication.bottom_ui.HomeFragment;
import com.example.myapplication.models.NewsItem;
import com.faltenreich.skeletonlayout.Skeleton;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;

public class FeedNews2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feednews2, container, false);
    }

    TextView txtNewsTitle;
    TextView txtNewsBody;
    ImageView imgNews;
    WebView webView;
    String fullUrl;
    String novo="";
    Skeleton skeleton;
    NestedScrollView nestedScrollView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainScreen) getActivity()).appBarLayout.setExpanded(true);
        ((MainScreen) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainScreen) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainScreen) getActivity()).toolbar.setTitle(getArguments().getCharSequence("txtNewsTitle"));
        nestedScrollView = view.findViewById(R.id.scrollLayout);


        //txtNewsTitle = view.findViewById(R.id.txtNewsTitle);
        txtNewsBody = view.findViewById(R.id.txtNewsBody);
        //this.imgNews = view.findViewById(R.id.imgNews);
        //String imgNews = getArguments().getString("imgNews");
        //txtNewsTitle.setText(getArguments().getCharSequence("txtNewsTitle"));
        skeleton = view.findViewById(R.id.skeleton);
        skeleton.showSkeleton();
        /*
        DynamicImageGetter.with(getContext())
                .load(getArguments().getString("txtNewsBody"))
                .mode(DynamicImageGetter.FULL_WIDTH)
                .into(txtNewsBody);
        //txtNewsBody.setText(getArguments().getCharSequence("txtNewsBody"));

        Glide.with(getContext())
                .load(imgNews)
                .into(this.imgNews);

         */

        fullUrl = "https://jornaldeangola.ao"+(getArguments().getString("newsLink"));
        //webView = view.findViewById(R.id.webView);
        //webView.setWebViewClient(new WebViewClient());
        //webView.getSettings().setJavaScriptEnabled(true);
        new MyTask().execute();
        //webView.loadUrl(fullUrl);
        //webView.loadData(getArguments().getString("txtNewsBody"),"text/html",null);


        HomeFragment homeFragment = (HomeFragment)getActivity()
                .getSupportFragmentManager()
                .findFragmentByTag("homeFragment");
        homeFragment.LockViewPagerSwipe();

    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String title ="";
            Document doc;
            try {
                doc = Jsoup.connect(fullUrl).get();
                Element element = doc.getElementsByClass("detalhe-noticia").get(0);
                title = element.toString();
                novo = element.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            //Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            //webView.loadData(result,"text/html",null);
            result = result.replace("/fotos","https://jornaldeangola.ao/fotos");
            //txtNewsBody.setText(txtNewsBody.getText().toString().replace());
            if (txtNewsBody!=null){
                DynamicImageGetter.with(getContext())
                        .load(result)
                        .mode(DynamicImageGetter.FULL_WIDTH)
                        .into(txtNewsBody);
                skeleton.showOriginal();
            }
            else
                Toast.makeText(getContext(), "é null", Toast.LENGTH_SHORT).show();
        }
    }

}
