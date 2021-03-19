package com.project.graphapp3.model;

import java.util.List;

public class Path {
    static int [][] adjMatrix;
    static  List<List<Integer>> path;
    public  void setAdjMatrix (int [][] adjMatrix) {
        this.adjMatrix = adjMatrix;
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
