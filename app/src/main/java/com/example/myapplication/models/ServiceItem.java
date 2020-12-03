package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class ServiceItem {


    @SerializedName("id")
    private int idService;

    @SerializedName("imagem")
    private String imgService;

    @SerializedName("nome")
    private String txtService;

    @SerializedName("descricao")
    private String txtDescService;

    @SerializedName("id_unidade")
    private int idUnity;

    public ServiceItem(int idService, String imgService, String txtService, String txtDescService, int idUnity) {
        this.idService = idService;
        this.imgService = imgService;
        this.txtService = txtService;
        this.txtDescService = txtDescService;
        this.idUnity = idUnity;
    }

    public int getIdService() {
        return idService;
    }

    public String getImgService() {
        return imgService;
    }

    public String getTxtService() {
        return txtService;
    }

    public String getTxtDescService() {
        return txtDescService;
    }

    public int getIdUnity() {
        return idUnity;
    }
}
