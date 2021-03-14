package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class RequestItem {

    @SerializedName("id")
    private int activityId;

    @SerializedName("reparticao_id")
    private int activityDivision;

    @SerializedName("servico_id")
    private int activityService;

    @SerializedName("created_at")
    private String activityDate;

    @SerializedName("estado_id")
    private int activityStatus;

    @SerializedName("motivo")
    private String txtReason;

    private String txtActivityStatus;

    private String txtActivityService;

    private String txtActivityDivision;

    private String txtActivityDivisionType;

    private String txtActivityDivisionAddress;

    private String txtActivityDivisionCounty;

    public RequestItem(int activityId, int activityDivision, int activityService,
                       String activityDate, int activityStatus, String txtActivityService,
                       String txtActivityDivision,String txtActivityStatus,String txtReason,
                       String txtActivityDivisionType,String txtActivityDivisionAddress, String txtActivityDivisionCounty) {
        this.activityId = activityId;
        this.activityDivision = activityDivision;
        this.activityService = activityService;
        this.activityDate = activityDate;
        this.activityStatus = activityStatus;
        this.txtActivityService = txtActivityService;
        this.txtActivityDivision = txtActivityDivision;
        this.txtActivityDivisionType = txtActivityDivisionType;
        this.txtActivityDivisionAddress =txtActivityDivisionAddress;
        this.txtActivityDivisionCounty = txtActivityDivisionCounty;
        this.txtActivityStatus = txtActivityStatus;
        this.txtReason = txtReason;

    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getActivityDivision() {
        return activityDivision;
    }

    public void setActivityDivision(int activityDivision) {
        this.activityDivision = activityDivision;
    }

    public int getActivityService() {
        return activityService;
    }

    public void setActivityService(int activityService) {
        this.activityService = activityService;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getTxtActivityService() {
        return txtActivityService;
    }

    public void setTxtActivityService(String txtActivityService) {
        this.txtActivityService = txtActivityService;
    }

    public String getTxtActivityDivision() {
        return txtActivityDivision;
    }

    public void setTxtActivityDivision(String txtActivityDivision) {
        this.txtActivityDivision = txtActivityDivision;
    }

    public String getTxtActivityDivisionType() {
        return txtActivityDivisionType;
    }

    public void setTxtActivityDivisionType(String txtActivityDivisionType) {
        this.txtActivityDivisionType = txtActivityDivisionType;
    }

    public String getTxtActivityDivisionAddress() {
        return txtActivityDivisionAddress;
    }

    public void setTxtActivityDivisionAddress(String txtActivityDivisionAddress) {
        this.txtActivityDivisionAddress = txtActivityDivisionAddress;
    }

    public String getTxtActivityDivisionCounty() {
        return txtActivityDivisionCounty;
    }

    public void setTxtActivityDivisionCounty(String txtActivityDivisionCounty) {
        this.txtActivityDivisionCounty = txtActivityDivisionCounty;
    }

    public String getTxtActivityStatus() {
        return txtActivityStatus;
    }

    public void setTxtActivityStatus(String txtActivityStatus) {
        this.txtActivityStatus = txtActivityStatus;
    }

    public String getTxtReason() {
        return txtReason;
    }

    public void setTxtReason(String txtReason) {
        this.txtReason = txtReason;
    }
}
