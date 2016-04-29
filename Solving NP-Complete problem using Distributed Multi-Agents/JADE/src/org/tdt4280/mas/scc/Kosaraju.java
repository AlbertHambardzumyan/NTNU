// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.scc;

import org.tdt4280.mas.timer.Runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Albert Hambardzumyan
 */
public class Kosaraju {

    public List<List<Integer>> scc(List<Integer>[] graph) {
        final long startTime = System.nanoTime();

        int n = graph.length;
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            if (!visited[i])
                dfs(graph, visited, order, i);

        List<Integer>[] reverseGraph = new List[n];
        for (int i = 0; i < n; i++)
            reverseGraph[i] = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            for (int j : graph[i])
                reverseGraph[j].add(i);

        List<List<Integer>> components = new ArrayList<List<Integer>>();
        Arrays.fill(visited, false);
        Collections.reverse(order);

        for (int u : order)
            if (!visited[u]) {
                List<Integer> component = new ArrayList<Integer>();
                dfs(reverseGraph, visited, component, u);
                components.add(component);
            }

        Runtime.print(System.nanoTime() - startTime, this.getClass().getSimpleName());
        return components;
    }

    static void dfs(List<Integer>[] graph, boolean[] visited, List<Integer> res, int u) {
        visited[u] = true;
        for (int v : graph[u])
            if (!visited[v])
                dfs(graph, visited, res, v);
        res.add(u);
    }
}
