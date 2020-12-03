package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class DocumentItem {

    @SerializedName("id")
    private int idDocument;

    @SerializedName("nome")
    private String txtDocument;

    public DocumentItem(int idDocument, String txtDocument) {
        this.idDocument = idDocument;
        this.txtDocument = txtDocument;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public String getTxtDocument() {
        return txtDocument;
    }
}
