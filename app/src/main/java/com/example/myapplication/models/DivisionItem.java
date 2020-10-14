package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class DivisionItem {

    @SerializedName("id")
    private int id;
    @SerializedName("tempoAbertura")
    private String openTime;
    @SerializedName("tempoEncerramento")
    private String closeTime;
    @SerializedName("endereco")
    private String address;
    @SerializedName("idUnidade")
    private int idUnity;
    @SerializedName("idMunicipio")
    private int idCounty;
    @SerializedName("idTipo")
    private int idType;
    @SerializedName("imagem")
    private String imgDivision;
    @SerializedName("latitude")
    private double lat;
    @SerializedName("longitude")
    private double lng;

    public DivisionItem(int id, String openTime, String closeTime, String address, int idUnity,int idCounty, int idType, String imgDivision, double lat, double lng) {
        this.id = id;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.address = address;
        this.idUnity = idUnity;
        this.idCounty = idCounty;
        this.idType = idType;
        this.imgDivision = imgDivision;
        this.lat = lat;
        this.lng = lng;
    }



    public int getId() {
        return id;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getAddress() {
        return address;
    }

    public int getIdUnity() {
        return idUnity;
    }

    public int getIdCounty(){ return idCounty; }

    public int getIdType() {
        return idType;
    }

    public String getImgDivision() {
        return imgDivision;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
