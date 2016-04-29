// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.taskAdmin;

import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import org.tdt4280.mas.config.Config;
import org.tdt4280.mas.scc.Tarjan;

import java.util.List;

/**
 * @author Albert Hambardzumyan
 */

/**
 * Singleton
 */
public class TaskAdministrator {
    private static TaskAdministrator ourInstance = new TaskAdministrator();
    private ContainerController cc;

    public static TaskAdministrator getInstance() {
        return ourInstance;
    }

    private TaskAdministrator() {
    }


    public void resolveTask(List<Integer>[] graph, int maxNumberOfColors) throws StaleProxyException {

        /** compute SCC **/
        List<List<Integer>> components = findSCC(graph);

        /**  List<Integer>[] converting into int[][] */
        int[][] graphArray = convertListToArray(graph);

        /** create container and agents **/
        initContainer();
        try {
            initTestAgents(graph.length);
        } catch (StaleProxyException e) {
            System.out.println("Internal Error: could not create the agent");
        }
        try {
            initDeliverer(graphArray, maxNumberOfColors, components);
        } catch (StaleProxyException e) {
            System.out.println("Internal Error: could not create the agent");
        }
    }

    /**
     * finding Strongly connected components
     */
    private List<List<Integer>> findSCC(List<Integer>[] graph) {
        System.out.println("Computing Strongly Connected Components ...");
        //List<List<Integer>> components = new Kosaraju().scc(graph);
        List<List<Integer>> components = new Tarjan().scc(graph);
        System.out.println("Done ...\nSCC: " + components);
        System.out.println("Needs " + components.size() + " Agents");

        return components;
    }

    /**
     * init the container *
     */
    private void initContainer() {
        Profile p = new ProfileImpl();
        jade.core.Runtime rt = Runtime.instance();
        cc = rt.createMainContainer(p);
    }

    /**
     * changing graph representation
     */
    private int[][] convertListToArray(List<Integer>[] graph) {
        int n = graph.length;
        int[][] graphArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graphArray[i][j] = 0;
            }
        }
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].size(); j++) {
                graphArray[i][graph[i].get(j)] = 1;
            }
        }
        return graphArray;
    }

    /**
     * create test agents
     */
    private void initTestAgents(int vertices) throws StaleProxyException {
        int[] color = new int[vertices];
        int index = 0;
        for (int i = 0; i < Config.AGENTS_NUMBER; i++) {
            Object[] arguments = new Object[4];
            arguments[3] = color;
            AgentController ac = cc.createNewAgent("A" + index, "org.tdt4280.mas.agent.Agents", arguments);
            ac.start();
            index++;
        }
    }

    /**
     * create deliverer agent for searching task solver agents
     */
    private void initDeliverer(int[][] graphArray, int maxNumberOfColors, List<List<Integer>> components) throws StaleProxyException {
        Object[] arguments = new Object[4];
        arguments[0] = graphArray;
        arguments[1] = maxNumberOfColors;
        arguments[2] = components;
        AgentController ac = cc.createNewAgent("Deliverer", "org.tdt4280.mas.deliverer.Deliverer", arguments);
        ac.start();
    }
}