// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.proposal;

import jade.core.AID;

/**
 * @author Albert Hambardzumyan
 */
public class Proposal {
    private AID proposer;
    private String problemJSON;
    private long time;

    public Proposal(AID proposer, String problemJSON, long time) {
        this.proposer = proposer;
        this.problemJSON = problemJSON;
        this.time = time;
    }

    public AID getProposer() {
        return proposer;
    }

    public void setProposer(AID proposer) {
        this.proposer = proposer;
    }

    public String getProblemJSON() {
        return problemJSON;
    }

    public void setProblemJSON(String problemJSON) {
        this.problemJSON = problemJSON;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}