package com.project.graphapp3;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;

public class MatrixInput extends AppCompatActivity {
    TextView textView;
    TextView textViewDist;
    TextView textViewShortPath;
    TextView nodesNumberView;
    EditText editMatrix;
    Button button;
    EditText node1;
    EditText node2;
    LinearLayout infoContainer;
    int state=0;
    int [][] prevMatrix;
    int [][] matrix;
    int input1 =0, input2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_input);
        textView = findViewById(R.id.textView2);
        nodesNumberView = findViewById(R.id.nodesNumberView);
        infoContainer = findViewById(R.id.infoContainer);
        editMatrix = findViewById(R.id.editTextTextMultiLine);
        button = findViewById(R.id.button);
        infoContainer.setVisibility(View.INVISIBLE);
        node1 = findViewById(R.id.editTextNumberDecimal);
        node2 = findViewById(R.id.editTextNumberDecimal2);
        textViewDist = findViewById(R.id.dist);
        textViewShortPath= findViewById(R.id.path);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
        if(state == 0) {
            String errorMessage = checkMatrix();
            ErrorCheck(errorMessage);
        }
        else {
            String errorMessage = checkMatrix();
            if(compareMatrix()==false){
                state=0;
                ErrorCheck(errorMessage);
            }
            else {
                errorMessage=checkNodes();
            if (errorMessage == null){
                textView.setText("");
                Dijkstra alg = new Dijkstra(matrix, matrix.length);
                int dist = alg.dijkstra(input1,input2);
                if(dist!=-1){
                textViewDist.setText("Shortest distance = "+dist);
                int [] path = alg.getShortestPath();
                String pathText = "Shortest path : "+path[0];
                for(int i =1; i<path.length; i++){
                    pathText += (" -> "+path[i]);
                }
                textViewShortPath.setText(pathText);
                }else textViewDist.setText("There is no path");
            }
            else {
                textView.setText(errorMessage);
                textView.setTextColor(Color.parseColor("#ee3300"));
            }}
        }
    }
    private  boolean compareMatrix (){
        if(matrix == null || prevMatrix.length != matrix.length){
            return false;
        }
        for(int i = 0; i<matrix.length; i++){
            for (int j=0; j <matrix[i].length; j++){
                if(matrix[i][j] != prevMatrix[i][j]) return false;
            }
        }
        return true;
    }
    private void ErrorCheck(String errorMessage){
        if (errorMessage == null) {
            textView.setText("");
            prevMatrix = matrix;
            state=1;
            nodesNumberView.setText("number of nodes : " + matrix.length);
            node1.setText("");
            node2.setText("");
            infoContainer.setVisibility(View.VISIBLE);
        } else {
            textView.setText(errorMessage);
            textView.setTextColor(Color.parseColor("#ee3300"));
        }
    }
    private String checkMatrix() {
        String errorMessage = null;
        textViewDist.setText("");
        textViewShortPath.setText("");
        String rawMatrixString = editMatrix.getText().toString().trim().replaceAll("[ ]","");
        if(rawMatrixString.contains(",,")){
            errorMessage = "skiped number betwin ',' and ','";
            matrix = null;
            return errorMessage;
        }
        String[] text =rawMatrixString.split("\n");
        int width = text[0].split(",").length;
        int height = text.length;
        if(width != height){
            errorMessage = "wrong size of matrix!\nwidth and height must be equals\nwidth["+width+"] height["+height+"]";
            matrix = null;
            return errorMessage;
        }
        for (int i = 0; i < text.length; i++) {
            String[]matrixRow=text[i].split(",");
            if (matrixRow.length != width) {
                errorMessage = "wrong matrix format! row [" + i + "] length:" + matrixRow.length + " but must be : " + width;
                matrix=null;
                return errorMessage;
            }
            for (int j = 0; j < text[i].length(); j++) {
                char t = text[i].charAt(j);
                if (!Character.isDigit(t) && t != ',') {
                    errorMessage = "wrong character! line[" + i + "] position is string line[" + j + "]";
                    matrix=null;
                    return errorMessage;
                }
            }
        }
        matrix =new int[height][width];
        for (int i = 0; i < height; i++) {
            String [] numbers = text[i].split(",");
            for (int j = 0; j < width; j++) {
                matrix[i][j]=Integer.parseInt(numbers[j]);
            }
        }

        return errorMessage;
    }

    private String checkNodes() {
        String errorMessage = null;
        textViewDist.setText("");
        textViewShortPath.setText("");
        try{
        input1 = Integer.parseInt(node1.getText().toString());
        input2 = Integer.parseInt(node2.getText().toString());
        }catch (Exception e){
            errorMessage = "Empty nodes. Enter the nodes for finding the path";
            return  errorMessage;
        }
        if(input1 <1 || input1 > matrix.length || input2 <1 || input2 > matrix.length){
            errorMessage = "Not existing node";
            return errorMessage;
        }

        return errorMessage;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}