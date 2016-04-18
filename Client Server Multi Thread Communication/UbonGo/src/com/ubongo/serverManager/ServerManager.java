package com.ubongo.serverManager;


import android.os.AsyncTask;
import com.ubongo.config.Config;
import com.ubongo.dataTransfer.RequestPackage;
import com.google.gson.Gson;
import com.ubongo.dataTransfer.ResponsePackage;
import com.ubongo.responseManager.ResponseManager;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerManager extends AsyncTask<Void, Void, Void> {

    private static ServerManager ourInstance = new ServerManager();

    public static ServerManager getInstance() {
        return ourInstance;
    }


    private PrintWriter out;
    private Gson gson;

    private ServerManager() {
    }

    @Override
    protected Void doInBackground(Void... params) {
        connect();
        return null;
    }

    public void connect() {
        try {
            //connect to server
            InetAddress serverAddress = InetAddress.getByName(Config.HOST);
            Socket socket = new Socket(serverAddress, Config.PORT);
            gson = new Gson();

            //open text oriented input and output channels
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try {
                while (true) {
                    String line = in.readLine();
                    if (line == null) break;
                    ResponsePackage responsePackage = gson.fromJson(line, ResponsePackage.class);
                    //System.out.println(responsePackage.getId() + " : " + responsePackage.getResponseStatus() + " : " + responsePackage.getResponseContent() + " : " + responsePackage.getResponseError());

                    switch (responsePackage.getId()) {
                        case 1:
                            // response from startLobby
                            ResponseManager.getInstance().startLobbyResponse(responsePackage);
                            break;
                        case 2:
                            // response from joinPlayer
                            ResponseManager.getInstance().joinPlayerResponse(responsePackage);
                            break;
                        case 3:
                            // response from setDifficulty
                            ResponseManager.getInstance().setDifficultyResponse(responsePackage);
                            break;
                        case 4:
                            // response from finishGame
                            ResponseManager.getInstance().finishGameResponse(responsePackage);
                            break;
                        case 5:
                            // response from leaveGame
                            ResponseManager.getInstance().leaveGameResponse(responsePackage);
                            break;
                        case 6:
                            // response from startGame
                            ResponseManager.getInstance().startGameResponse(responsePackage);
                            break;
                        case 7:
                            // response from removePlayer
                            ResponseManager.getInstance().removePlayerResponse(responsePackage);
                            break;
                        default:
                            System.out.println("\nCannot recognize the called method");
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println("\nCould not read the input");
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startLobby(String name) { //name argument needs only for logging on the server side, to see who is creating
        RequestPackage data = new RequestPackage(1, name, null, null, false);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }

    public void joinPlayer(String name, String pin, boolean ownerStatus) {
        RequestPackage data = new RequestPackage(2, name, pin, null, ownerStatus);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }

    public void setDifficulty(String pin, String difficulty) {
        RequestPackage data = new RequestPackage(3, null, pin, difficulty, false);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }

    public void finishGame(String name, String pin) {
        RequestPackage data = new RequestPackage(4, name, pin, null, false);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }

    public void leaveGame(String name, String pin) {
        RequestPackage data = new RequestPackage(5, name, pin, null, false);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }

    public void startGame(String name, String pin) {
        RequestPackage data = new RequestPackage(6, name, pin, null, false);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }

    public void removePlayer(String name, String pin) {
        RequestPackage data = new RequestPackage(7, name, pin, null, false);
        String json = gson.toJson(data);
        out.println(json);
        out.flush();
    }
}