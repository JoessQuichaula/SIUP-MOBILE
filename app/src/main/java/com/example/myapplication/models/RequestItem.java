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

    public RequestItem(int activityId, int activityDivision, int activityService,
                       String activityDate, int activityStatus, String txtActivityService,
                       String txtActivityDivision,String txtActivityStatus,String txtReason) {
        this.activityId = activityId;
        this.activityDivision = activityDivision;
        this.activityService = activityService;
        this.activityDate = activityDate;
        this.activityStatus = activityStatus;
        this.txtActivityService = txtActivityService;
        this.txtActivityDivision = txtActivityDivision;
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
