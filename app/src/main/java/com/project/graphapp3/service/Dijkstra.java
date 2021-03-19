package com.project.graphapp3.service;

import java.util.Stack;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Dijkstra {
    int INF = Integer.MAX_VALUE / 2; // "Бесконечность"
    int vNum; // количество вершин
    int path[];
    int[][] graph; // матрица смежности
    public Dijkstra (int[][] graph, int vNum){
        this.graph = new int[vNum][vNum];
        for(int i =0; i<vNum; i++){
            for (int j =0; j<vNum; j++){
                if(graph[i][j] != 0){
                    this.graph[i][j] = graph[i][j];
                }
                else this.graph[i][j] = INF;
            }
        }
        this.vNum = vNum;

    }
    public int dijkstra(int start, int end) {
        if(start == end){
            path = new int[2];
            path[0]=start; path[1] = end;
            return 0;
        }
        boolean[] used = new boolean [vNum]; // массив пометок
        int[] dist = new int [vNum];
        int[] prev = new int [vNum];// массив расстояния. dist[v] = минимальное_расстояние(start, v)
        fill(dist, INF); // устанаавливаем расстояние до всех вершин INF
        fill(prev, -1);
        dist[start-1] = 0; // для начальной вершины положим 0
        for (;;) {
            int v = -1;
            for (int nv = 0; nv < vNum; nv++) // перебираем вершины
                if (!used[nv] && dist[nv] < INF && (v == -1 || dist[v] > dist[nv])) // выбираем самую близкую непомеченную вершину
                    v = nv;
                if (v == -1) break; // ближайшая вершина не найдена
            used[v] = true; // помечаем ее
            for (int nv = 0; nv < vNum; nv++)
                if (!used[nv] && graph[v][nv] < INF) { // для всех непомеченных смежных
                    if(dist[nv] > dist[v]+graph[v][nv]) prev[nv] = v;
                    dist[nv] = min(dist[nv], dist[v] + graph[v][nv]);

                }
        }
        Stack stack = new Stack();
        for (int v = end-1; v != -1; v = prev[v]) {
            stack.push(v);
        }
        path = new int [stack.size()];
        for (int i = 0; i < path.length; i++)
            path[i] = (int) stack.pop() + 1 ;
        if(dist[end-1] == INF){ path = null;return -1;}
       return dist[end-1];
    }
    public int[] getShortestPath (){
        return path;
    }
}
