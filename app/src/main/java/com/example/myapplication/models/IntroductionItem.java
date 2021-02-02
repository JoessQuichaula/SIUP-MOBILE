package com.example.myapplication.models;

public class IntroductionItem {

    private int apresentationImg;
    private String apresentationTitle;
    private String apresentationBody;

    public IntroductionItem(int apresentationImg, String apresentationTitle, String apresentationBody) {
        this.apresentationImg = apresentationImg;
        this.apresentationTitle = apresentationTitle;
        this.apresentationBody = apresentationBody;
    }

    public int getApresentationImg() {
        return apresentationImg;
    }

    public void setApresentationImg(int apresentationImg) {
        this.apresentationImg = apresentationImg;
    }

    public String getApresentationTitle() {
        return apresentationTitle;
    }

    public void setApresentationTitle(String apresentationTitle) {
        this.apresentationTitle = apresentationTitle;
    }

    public String getApresentationBody() {
        return apresentationBody;
    }

    public void setApresentationBody(String apresentationBody) {
        this.apresentationBody = apresentationBody;
    }
}
