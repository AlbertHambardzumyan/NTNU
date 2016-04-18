// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package com.ubongo;

/**
 * @author Albert Hambardzumyan
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import com.example.UbonGo.R;
import com.ubongo.serverManager.ServerManager;

public class UbonGo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // force for internet connection permissions
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // connect to the server
        ServerManager.getInstance().execute();


        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startLobby("Albert");
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startLobby("Tester"); //create 2 different games: 1001 and 1002 if the server just started

        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Albert", "1001", true); // joined as owner: success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Armen", "1001", false); // joined as player: success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Tester", "555", true); // typed wrong pin. game does not exist: error 404 : no such game
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Tester", "1002", true); // success

        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().setDifficulty("1001", "2");  // set difficulty  : success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().setDifficulty("777", "2");  // set difficulty : error 404: no such game

        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().finishGame("Albert", "1001");  // finish the game : error 404 : game is not started even

        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startGame("Albert", "777"); // start the game : error 404: no such game
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startGame("Armen", "1001"); // start the game : error 404 : access denied, no owner  permissions
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startGame("John", "1001"); // start the game : error 404 : no such player in this game
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startGame("Albert", "1001"); // start the game : success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().startGame("Albert", "1001"); // start the game : error 404 : game started already


        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().setDifficulty("1001", "2");  // set difficulty : error 404: game started already, cannot change

        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().finishGame("Albert", "68");  // finish the game : error 404 : game not found
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().finishGame("Albert", "1001");  // finish the game : success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().finishGame("Albert", "1001");  // finish the game : error 404 : no such game


        /** up until here only game 1002 remains with player Tester*/
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Armen", "1002", false); // joined as player: success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().leaveGame("Armen", "1002");       // leave the game: success - Tester still in the game

        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Armen", "1002", false); // joined as player: success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().joinPlayer("Anton", "1002", false); // joined as player: success
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().removePlayer("Anton", "1002");   // success - Armen and Tester in the game
        for (int i = 0; i < 99999999; i++) {
        }
        ServerManager.getInstance().removePlayer("Tester", "1002");   // success - game deleted although Armen was waiting: owner left
    }
}