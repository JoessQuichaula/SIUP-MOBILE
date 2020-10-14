package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class DivisionTypeItem {

    @SerializedName("id")
    private int id;

    @SerializedName("nome")
    private String name;

    public DivisionTypeItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
