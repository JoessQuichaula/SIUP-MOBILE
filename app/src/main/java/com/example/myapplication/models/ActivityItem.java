package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivityItem {





    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<RequestItem> requestItems;

    @SerializedName("last_page")
    private int lastPage;


    public ActivityItem(int current_page, List<RequestItem> requestItems,int lastPage) {
        this.current_page = current_page;
        this.requestItems = requestItems;
        this.lastPage = lastPage;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<RequestItem> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<RequestItem> requestItems) {
        this.requestItems = requestItems;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
