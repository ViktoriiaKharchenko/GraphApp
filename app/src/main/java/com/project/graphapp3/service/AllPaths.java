package com.project.graphapp3.service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AllPaths {

    private void dfs(int[][] graph, List<List<Integer>> result, List<Integer> path, int start,int end){
        if(path.contains(start)) {
            return;
        }
        else {
            path.add(start);
            if(start == end) result.add(new ArrayList(path));
            else
            for(int[] v: graph) {
                if(v[0] == start){
                    dfs(graph, result, path, v[1],end);
                }
            }
         if(path.size() != 0)
            path.remove(path.size()-1);
      }
    }

    public List<List<Integer>> allPathsSourceTarget(int[][] graph,int start, int end) {
        List<List<Integer>> result = new ArrayList();
        List<Integer> path = new ArrayList();
        if(start == end){
            path.add(start);
            path.add(end);
            result.add(path);
            return result;
        }
        dfs(graph, result, path, start, end);
        return result;
    }

}


