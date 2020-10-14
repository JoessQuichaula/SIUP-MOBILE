package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class CountyItem {

    @SerializedName("nome")
    private String txtCounty;

    public CountyItem(String txtCounty) {
        this.txtCounty = txtCounty;
    }

    public String getTxtCounty() {
        return txtCounty;
    }

    public void setTxtCounty(String txtCounty) {
        this.txtCounty = txtCounty;
    }
}
