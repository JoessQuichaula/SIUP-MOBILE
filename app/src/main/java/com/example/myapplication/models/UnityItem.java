package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class UnityItem {

    @SerializedName("id")
    private int idUnity;

    @SerializedName("image")
    private String imgUnity;

    @SerializedName("nome")
    private String txtUnity;

    public UnityItem(int idUnity,String imgUnity, String txtUnity) {
        this.imgUnity = imgUnity;
        this.txtUnity = txtUnity;
        this.idUnity = idUnity;
    }

    public int getIdUnity() {
        return idUnity;
    }

    public String getImgUnity() {
        return imgUnity;
    }

    public String getTxtUnity() {
        return txtUnity;
    }
}
