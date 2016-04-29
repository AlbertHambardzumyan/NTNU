// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.deliverer;

import com.google.gson.Gson;
import jade.core.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;
import org.tdt4280.mas.problem.Problem;

import java.util.Iterator;
import java.util.List;

/**
 * @author Albert Hambardzumyan
 */
public class Deliverer extends Agent {
    private Gson gson;

    protected void setup() {
        super.setup();
        // Register the Deliverer Agent service in the yellow pages
        this.registerSolver();
        this.printDetails();

        gson = new Gson();

        Object[] args = getArguments();
        int[][] graph = (int[][]) args[0];
        int numOfColors = (Integer) args[1];
        List<List<Integer>> components = (List<List<Integer>>) args[2];
        int[] color = (int[]) args[3];
        for (int i = components.size(); i > 0; i--) {
            try {
                delay();
                this.findAgent(graph, components.get(i - 1), color, numOfColors);
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * register in yellow pages
     */
    protected void registerSolver() {
        DFAgentDescription desc = new DFAgentDescription();
        desc.setName(this.getAID());
        ServiceDescription d = new ServiceDescription();
        d.setName(this.getLocalName());
        d.setType("Deliverer");
        desc.addServices(d);
        try {
            DFService.register(this, desc);
            System.out.println("\nRegistered myself in the yellow pages: " + this.getAID());
        } catch (FIPAException e) {
            System.out.println("\nCould not register myself: " + this.getAID().getLocalName());
        }
    }

    /**
     * print more detail
     */
    protected void printDetails() {
        System.out.println("Local Name: " + getAID().getLocalName());
        System.out.println("GUID is: " + getAID().getName());
        System.out.println("Addresses: ");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }
    }

    public void findAgent(int[][] graphArray, List<Integer> component, int[] color, int maxNumberOfColors) throws StaleProxyException {
        Problem data = new Problem(graphArray, maxNumberOfColors, component, color);
        String json = gson.toJson(data);

        /** find the agent with min time */
        firstPriceAuction(json, "Coloring");
    }

    /**
     * finds the agent which solves in min about of time
     */
    private void firstPriceAuction(String problemObject, String problemType) {
        /** trying to find some agent */
        DFAgentDescription desc = new DFAgentDescription();
        ServiceDescription s = new ServiceDescription();
        s.setType(problemType);
        desc.addServices(s);
        DFAgentDescription[] agents = null;
        try {
            System.out.println("\nSearch for Agent ...");
            agents = DFService.search(this, desc);
        } catch (FIPAException e) {
            System.out.println("\nCould NOT Search the Agent");
        }
        System.out.println("Search is Done ...");

        /** Broadcasting if found some */
        if (agents != null) {
            System.out.println("Found Agents ... \nBroadcasting ...");
            ACLMessage msg = new ACLMessage(ACLMessage.CFP);
            int index = 0;
            for (DFAgentDescription d : agents) {
                msg.addReceiver(d.getName());
                System.out.println("Sending message to Agent " + index++);
            }
            msg.setContent(problemObject);
            this.send(msg);

            /** getting broadcast response */
            long minTime = Long.MAX_VALUE;
            AID min = null;
            for (int i = 0; i < agents.length; i++) {
                ACLMessage reply = this.blockingReceive();
                if (reply != null && reply.getPerformative() == ACLMessage.PROPOSE) {
                    long time = Long.parseLong(reply.getContent());
                    System.out.println("Got Broadcast Answer from Agent: " + reply.getSender() + "\n Requires: " + time);
                    if (time < minTime) {
                        minTime = time;
                        min = reply.getSender();
                    }
                }
            }

            /** reject all agents except the min time */
            ACLMessage reject = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
            for (DFAgentDescription d : agents) {
                if (!d.getName().equals(min)) {
                    reject.addReceiver(d.getName());
                    System.out.println("Sending reject to: " + d.getName());
                }
            }
            this.send(reject);

            /** accept the min one  */
            if (min != null) {
                ACLMessage accept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                accept.addReceiver(min);
                this.send(accept);
                System.out.println("Sending Accept to: " + min.getLocalName());
                ACLMessage answer = this.blockingReceive();
                if (answer != null && answer.getPerformative() == ACLMessage.INFORM && answer.getSender().equals(min)) {
                    System.out.println("Got Feedback. from: " + answer.getSender() + " Problem is Solved");
                }
            }
        } else {
            System.err.println("Could NOT Find Any Agent");
        }
    }

    private void delay(){
        for (int i = 0; i < 999999999; i++){}
    }
}