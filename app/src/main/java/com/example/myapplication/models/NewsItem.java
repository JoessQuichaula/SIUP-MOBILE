package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class NewsItem {

    @SerializedName("image")
    private String imgNews;

    @SerializedName("title")
    private String txtNewsTitle;

    @SerializedName("body")
    private String txtNewsBody;

    private String txtNewsTime;

    private String newsLink;

    public NewsItem(String imgNews, String txtNewsTitle, String txtNewsBody, String txtNewsTime,String newsLink) {
        this.imgNews = imgNews;
        this.txtNewsTitle = txtNewsTitle;
        this.txtNewsBody = txtNewsBody;
        this.txtNewsTime = txtNewsTime;
        this.newsLink = newsLink;
    }

    public String getImgNews() {
        return imgNews;
    }

    public String getTxtNewsTitle() {
        return txtNewsTitle;
    }

    public String getTxtNewsBody() {
        return txtNewsBody;
    }

    public String getTxtNewsTime() {
        return txtNewsTime;
    }

    public String getNewsLink() {
        return newsLink;
    }
}
