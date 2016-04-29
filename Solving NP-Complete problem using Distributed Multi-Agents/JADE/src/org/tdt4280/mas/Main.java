// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas;

import jade.wrapper.StaleProxyException;
import org.tdt4280.mas.config.Config;
import org.tdt4280.mas.taskAdmin.TaskAdministrator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Albert Hambardzumyan
 */
public class Main {
    public static void main(String[] args) throws StaleProxyException {
        List<Integer>[] graph = initGraph();
        TaskAdministrator.getInstance().resolveTask(graph, Config.MAX_NUMBER_OF_COLORS);
    }

    /**
     * create a graph *
     */
    private static List<Integer>[] initGraph() {
        int n = 6;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        graph[0].add(1);
        graph[1].add(2);
        graph[1].add(3);
        graph[1].add(4);
        graph[2].add(5);
        graph[3].add(4);
        graph[4].add(1);
        graph[4].add(5);
        graph[5].add(2);

        return graph;
    }
}
