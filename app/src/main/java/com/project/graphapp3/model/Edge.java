package com.project.graphapp3.model;

public class Edge {
    int length;
    GraphDot toDot;

    public Edge(int length, GraphDot toDot) {
        this.length = length;
        this.toDot = toDot;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public GraphDot getToDot() {
        return toDot;
    }

    public void setToDot(GraphDot toDot) {
        this.toDot = toDot;
    }
}
