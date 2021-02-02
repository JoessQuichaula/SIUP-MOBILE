package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class ActivityStatusItem {

    @SerializedName("id")
    private int id;

    @SerializedName("estado")
    private String status;

    public ActivityStatusItem(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
