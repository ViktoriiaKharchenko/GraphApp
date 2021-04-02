package com.project.graphapp3.service;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static class FileUtils {
        private static final String TAG = "MEDIA";

        @RequiresApi(api = Build.VERSION_CODES.O)
        public static void saveStringToFile(String textToSave, AppCompatActivity context){
            File root = android.os.Environment.getExternalStorageDirectory();
            String time = LocalDateTime.now().toString().replaceAll("[:]","_");
            File dir = new File (root.getAbsolutePath() + "/download");
            dir.mkdirs();
            File file = new File(dir, "graph"+time+".txt");

            try {
                FileOutputStream f = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(f);
                pw.println(textToSave);
                pw.flush();
                pw.close();
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i(TAG, "******* File not found. Did you" +
                        " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "File written to : "+file, Toast.LENGTH_LONG).show();

        }
    }
}


