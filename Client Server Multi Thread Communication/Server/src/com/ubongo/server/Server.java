// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package com.ubongo.server;

/**
 * @author Albert Hambardzumyan
 */
import com.ubongo.clientTread.ClientThread;
import com.ubongo.config.Config;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    static ServerSocket serverSocket;

    public Server() {
    }

    public void run() {
        boolean listening = true;
        try {
            serverSocket = new ServerSocket(Config.PORT);
            System.out.println("\nServer connected on port: " + Config.PORT);
        } catch (IOException e) {
            System.out.println("\nCould not listen on port: " + Config.PORT);
            System.exit(-1);
        }

        while (listening) {
            try {
                Socket socket = serverSocket.accept();            //waiting for client
                System.out.println("\nAccepted Client from " + socket.getInetAddress());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                new ClientThread(out, in).start();

            } catch (IOException e) {
                System.out.println("\nCould not connect the client");
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("\nCould not close the server socket");
            System.exit(-1);
        }
    }
}