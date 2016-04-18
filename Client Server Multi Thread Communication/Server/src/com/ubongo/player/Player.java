// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package com.ubongo.player;

/**
 * @author Albert Hambardzumyan
 */
import java.io.PrintWriter;

public class Player {
    private String name;
    private boolean ownerStatus;
    private PrintWriter out;


    public Player(String name, boolean ownerStatus, PrintWriter out) {
        this.name = name;
        this.ownerStatus = ownerStatus;
        this.out = out;
    }

    public String getName() {
        return name;
    }

    public boolean getOwnerStatus(){
        return ownerStatus;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerStatus(boolean ownerStatus){
        this.ownerStatus = ownerStatus;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
}