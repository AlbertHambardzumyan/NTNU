// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.agent;


import com.google.gson.Gson;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import org.tdt4280.mas.problem.Problem;
import org.tdt4280.mas.proposal.Proposal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tdt4280.mas.timer.Runtime;

/**
 * @author Albert Hambardzumyan
 */
public class Agents extends Agent {
    private int[][] graph;
    private int numOfColors;
    private List<Integer> component;
    private int[] color;

    private Gson gson;

    private List<Proposal> proposal = new ArrayList<Proposal>();

    private int index = 0;
    private int length;

    /**
     * general setup *
     */
    protected void setup() {
        super.setup();
        gson = new Gson();
        // Register the Graph Coloring service in the yellow pages
        this.registerSolver();
        this.printDetails();

        // add cycle behaviour to listen the requests **/
        this.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    System.err.println("\n" + getAID().getLocalName() + ": got message: " + msg.getContent());
                    System.err.println("Performative: " + msg.getPerformative());
                    switch (msg.getPerformative()) {
                        case ACLMessage.CFP:
                            handleCFP(msg);
                            break;
                        case ACLMessage.ACCEPT_PROPOSAL:
                            handleAccept(msg);
                            break;
                        case ACLMessage.REJECT_PROPOSAL:
                            handleReject(msg);
                            break;
                        default:
                            System.err.println("Can not read the input: " + msg);
                            break;
                    }
                }
            }

        });

        // get reference to color vector
        Object[] args = getArguments();
        color = (int[]) args[3];
    }

    /**
     * register in yellow pages *
     */
    protected void registerSolver() {
        DFAgentDescription desc = new DFAgentDescription();
        desc.setName(this.getAID());
        ServiceDescription d = new ServiceDescription();
        d.setName(this.getLocalName());
        d.setType("Coloring");
        desc.addServices(d);
        try {
            DFService.register(this, desc);
            System.out.println("\nRegistered myself in the yellow pages: " + this.getAID());
        } catch (FIPAException e) {
            System.out.println("\nCould NOT register myself: " + this.getAID().getLocalName());
        }
    }

    /**
     * more details
     */
    protected void printDetails() {
        // Print the details  of this agent
        System.out.println("Local Name: " + getAID().getLocalName());
        System.out.println("GUID: " + getAID().getName());
        System.out.println("Addresses: ");
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }
    }

    /**
     * handle broadcast, send back the estimated time to solve the problem
     */
    private void handleCFP(ACLMessage msg) {
        System.out.println(getAID().getLocalName() + ": got CFP message ...");
        String problem = msg.getContent();
        Problem data = gson.fromJson(problem, Problem.class);
        graph = data.getGraph();
        numOfColors = data.getNumOfColors();
        component = data.getComponent();

        //generate number for now, later will be based on machine params
        long time = (long) (10 * Math.random());
        Proposal p = new Proposal(msg.getSender(), problem, time);
        this.proposal.add(p);
        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.PROPOSE);
        reply.setContent("" + time);
        this.send(reply);
    }

    /**
     * accept the offer, solve, and inform
     */
    private void handleAccept(ACLMessage msg) {
        System.out.println(getAID().getLocalName() + ": got accept message ...");
        Proposal p = findProposal(msg.getSender());
        this.proposal.remove(p);
        //main solver
        graphColor(graph, numOfColors, component, color);

        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        this.send(reply);
        System.out.println(getAID().getLocalName() + ": problem solving is Done ...");
    }

    /**
     * delete the offer from the history
     */
    private void handleReject(ACLMessage msg) {
        System.out.println(getAID().getLocalName() + ": got reject message ...");
        Proposal p = findProposal(msg.getSender());
        if (p != null) proposal.remove(p);
    }

    /**
     * helper to find the offer
     */
    private Proposal findProposal(AID sender) {
        for (Proposal p : proposal) {
            if (p.getProposer().equals(sender)) return p;
        }
        return null;
    }


    /**
     * main coloring algorithm
     */
    public void graphColor(int[][] g, int numOfColors, List component, int[] color) {
        final long startTime = System.nanoTime();
        System.out.println(component.toString());
        length = component.size();
        index = 0;
        Iterator itr = component.iterator();
        int start = Integer.valueOf(itr.next().toString());
        System.out.print("\nColored Vertices: ");

        try {
            solve(start, color, itr, g, numOfColors);
            System.out.println("No solution");
        } catch (Exception e) {
            display(color);
            Runtime.print(System.nanoTime() - startTime, this.getAID().getName().substring(0, 2) + ":");
        }
    }

    /**
     * function to assign colors recursively *
     */
    public void solve(int v, int[] color, Iterator itr, int[][] g, int numOfColors) throws Exception {

        /** base case - solution found **/
        if (index == length)
            throw new Exception("Solution found");

        System.out.print(v + " ");
        /** try all colours **/
        for (int c = 1; c <= numOfColors; c++) {
            if (isPossible(v, c, color, g)) {
                /** assign and proceed with next vertex **/
                color[v] = c;
                int next = 0;
                if (itr.hasNext()) {
                    next = Integer.valueOf(itr.next().toString());
                }
                index++;
                solve(next, color, itr, g, numOfColors);

                /** wrong assignement **/
                color[v] = 0;
            }
        }
    }

    /**
     * function to check if it is valid to allot that color to vertex *
     */
    private boolean isPossible(int v, int c, int[] color, int[][] graph) {
        int V = graph.length;
        for (int i = 0; i < V; i++)
            if (graph[v][i] == 1 && c == color[i])
                return false;
        for (int j = 0; j < V; j++)
            if (graph[j][v] == 1 && c == color[j])
                return false;
        return true;
    }

    /**
     * display solution *
     */
    private void display(int[] color) {
        System.out.print("\nColors : ");
        for (int aColor : color) System.out.print(aColor + " ");
        System.out.println();
    }
}