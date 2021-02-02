package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class NewUserItem {


    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("telemovel")
    private String phoneNumber;
    @SerializedName("password")
    private String password;
    @SerializedName("bilhete_identidade")
    private String identityPass;
    private MultipartBody.Part identityPassFile;

    public NewUserItem(String name, String email, String phoneNumber, String password, String identityPass, MultipartBody.Part identityPassFile) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.identityPass = identityPass;
        this.identityPassFile = identityPassFile;
    }


}
