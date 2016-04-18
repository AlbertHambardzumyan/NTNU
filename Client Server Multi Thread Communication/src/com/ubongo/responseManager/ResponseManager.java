// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package com.ubongo.responseManager;

/**
 * @author Albert Hambardzumyan
 */
import com.ubongo.dataTransfer.ResponsePackage;

public class ResponseManager {
    private static ResponseManager ourInstance = new ResponseManager();

    public static ResponseManager getInstance() {
        return ourInstance;
    }

    private ResponseManager() {
    }

    public void startLobbyResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }

    public void joinPlayerResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }

    public void setDifficultyResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }

    public void finishGameResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }

    public void leaveGameResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }

    public void startGameResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }

    public void removePlayerResponse(ResponsePackage responsePackage) {
        System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());
    }
}