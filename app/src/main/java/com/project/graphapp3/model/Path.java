package com.project.graphapp3.model;

import java.io.Serializable;
import java.util.List;

public class Path implements Serializable {
    int [][] adjMatrix;
    List<List<Integer>> path;
    int nodesNum;
    public void setAdjMatrix (int [][] adjMatrix2) {
        adjMatrix = adjMatrix2;
    }

    public int getNodesNum() {
        return nodesNum;
    }

    public void setNodesNum(int nodesNum) {
        this.nodesNum = nodesNum;
    }

    public int[][] getAdjMatrix(){
        return adjMatrix;
    }
    public  void setPath (List<List<Integer>> path){
        this.path = path;
    }
    public List<List<Integer>> getPath (){
        return path;
    }
}
