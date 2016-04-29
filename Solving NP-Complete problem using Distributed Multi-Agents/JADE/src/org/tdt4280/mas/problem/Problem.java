// Copyright (c) 2016 Albert Hambardzumyan
// All rights reserved.
// This software is released under the BSD license.
package org.tdt4280.mas.problem;

import java.util.List;

/**
 * @author Albert Hambardzumyan
 */
public class Problem {
    private int[][] graph;
    private int numOfColors;
    private List<Integer> component;
    private int[] color;

    public Problem(int[][] graph, int numOfColors, List<Integer> component, int[] color) {
        this.graph = graph;
        this.numOfColors = numOfColors;
        this.component = component;
        this.color = color;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public int getNumOfColors() {
        return numOfColors;
    }

    public void setNumOfColors(int numOfColors) {
        this.numOfColors = numOfColors;
    }

    public List<Integer> getComponent() {
        return component;
    }

    public void setComponent(List<Integer> component) {
        this.component = component;
    }

}