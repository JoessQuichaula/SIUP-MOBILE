package com.example.myapplication.models;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserItem implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("role_id")
    private int roleId;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("created_at")
    private String CreationDate;
    @SerializedName("telemovel")
    private String phoneNumber;

    public UserItem(int id, int roleId, String name, String email, String creationDate, String phoneNumber) {
        this.id = id;
        this.roleId = roleId;
        this.name = name;
        this.email = email;
        CreationDate = creationDate;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public int getRoleId() {
        return roleId;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
