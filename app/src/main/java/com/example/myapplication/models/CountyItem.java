package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class CountyItem {


    @SerializedName("id")
    private int idCounty;

    @SerializedName("nome")
    private String txtCounty;

    public CountyItem(int idCounty, String txtCounty) {
        this.idCounty = idCounty;
        this.txtCounty = txtCounty;
    }

    public int getIdCounty() {
        return idCounty;
    }

    public void setIdCounty(int idCounty) {
        this.idCounty = idCounty;
    }

    public String getTxtCounty() {
        return txtCounty;
    }

    public void setTxtCounty(String txtCounty) {
        this.txtCounty = txtCounty;
    }
}
