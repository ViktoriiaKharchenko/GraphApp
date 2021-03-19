package com.project.graphapp3.model;

import java.util.HashSet;
import java.util.Set;

public class GraphDot {
    int name;
    float x,y,nameX,nameY,arowX,arowY;
    Set<Integer> edges = new HashSet<>();

    public GraphDot() {
    }

    public GraphDot(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getNameX() {
        return nameX;
    }

    public void setNameX(float nameX) {
        this.nameX = nameX;
    }

    public float getNameY() {
        return nameY;
    }

    public void setNameY(float nameY) {
        this.nameY = nameY;
    }

    public float getArowX() {
        return arowX;
    }

    public void setArowX(float arowX) {
        this.arowX = arowX;
    }

    public float getArowY() {
        return arowY;
    }

    public void setArowY(float arowY) {
        this.arowY = arowY;
    }

    public Set<Integer> getEdges() {
        return edges;
    }

    public void setEdges(Set<Integer> edges) {
        this.edges = edges;
    }
}
