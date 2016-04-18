package com.ubongo.dataTransfer;


import java.io.Serializable;

public class ResponsePackage implements Serializable {
    private int id;
    private int responseStatus;
    private String responseContent;
    private String responseError;


    public ResponsePackage(int id, int responseStatus, String responseContent, String responseError) {
        this.id = id;
        this.responseStatus = responseStatus;
        this.responseContent = responseContent;
        this.responseError = responseError;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public String getResponseError() {
        return responseError;
    }

    public void setResponseError(String responseError) {
        this.responseError = responseError;
    }
}