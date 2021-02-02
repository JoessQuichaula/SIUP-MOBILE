package com.example.myapplication.models;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

public class DocumentItem {

    @SerializedName("id")
    private int idDocument;

    @SerializedName("nome")
    private String txtDocument;

    private Uri documentUri;

    private String txtFileName;

    private String txtFileSize;

    private String txtFileExt;

    public DocumentItem(int idDocument, String txtDocument,Uri documentUri, String txtFileName, String txtFileSize, String txtFileExt) {
        this.idDocument = idDocument;
        this.txtDocument = txtDocument;
        this.documentUri = documentUri;
        this.txtFileName = txtFileName;
        this.txtFileSize = txtFileSize;
        this.txtFileExt = txtFileExt;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public String getTxtDocument() {
        return txtDocument;
    }

    public Uri getDocumentUri() {
        return documentUri;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public String getTxtFileSize() {
        return txtFileSize;
    }

    public String getTxtFileExt() {
        return txtFileExt;
    }

    public void setDocumentUri(Uri documentUri) {
        this.documentUri = documentUri;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public void setTxtFileSize(String txtFileSize) {
        this.txtFileSize = txtFileSize;
    }

    public void setTxtFileExt(String txtFileExt) {
        this.txtFileExt = txtFileExt;
    }

}
