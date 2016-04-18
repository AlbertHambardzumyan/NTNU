// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package com.ubongo.clientTread;

/**
 * @author Albert Hambardzumyan
 */
import com.google.gson.Gson;
import com.ubongo.dataTransfer.RequestPackage;
import com.ubongo.gameHolder.GameHolder;

import java.io.*;

public class ClientThread extends Thread {
    PrintWriter out;
    BufferedReader in;
    Gson gson;

    public ClientThread(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
        this.gson = new Gson();
    }

    public void run() {
        try {
            while (true) {
                String line = in.readLine();
                if (line == null) break;
                RequestPackage data = gson.fromJson(line, RequestPackage.class);

                switch (data.getId()) {
                    case 1:
                        // get argument name // needs only for logging, to see who is creating
                        GameHolder.getInstance().startLobby(data.getName(), out, 1);
                        break;
                    case 2:
                        // get argument name, pin, owner status
                        GameHolder.getInstance().joinPlayer(data.getName(), data.getPin(), data.getOwnerStatus(), out, 2);
                        break;
                    case 3:
                        // get argument difficult, pin
                        GameHolder.getInstance().setDifficulty(data.getDifficulty(), data.getPin(), out, 3);
                        break;
                    case 4:
                        // get argument name, pin
                        GameHolder.getInstance().finishGame(data.getName(), data.getPin(), out, 4);
                        break;
                    case 5:
                        // get argument name, pin
                        GameHolder.getInstance().leaveGame(data.getName(), data.getPin(), out, 5);
                        break;
                    case 6:
                        // get argument name, pin
                        GameHolder.getInstance().startGame(data.getName(), data.getPin(), out, 6);
                        break;
                    case 7:
                        // get argument name, pin
                        GameHolder.getInstance().removePlayer(data.getName(), data.getPin(), out, 7);
                        break;
                    default:
                        System.out.println("\nCannot recognize the called method");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("\nCould not read the input");
        }
    }

}