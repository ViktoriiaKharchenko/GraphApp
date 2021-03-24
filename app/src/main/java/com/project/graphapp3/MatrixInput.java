package com.project.graphapp3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.project.graphapp3.model.Path;
import com.project.graphapp3.service.AllPaths;
import com.project.graphapp3.service.Dijkstra;

import java.util.ArrayList;
import java.util.List;

public class MatrixInput extends AppCompatActivity {
    private static final String TAG = "MatrixInput";
    private static final int REQUEST_CODE = 6384; // onActivityResult request

    TextView textView;
    TextView textViewDist;
    TextView textViewShortPath;
    TextView textViewAllPaths;
    EditText nodesNumberText;
    EditText editMatrix;
    Button button;
    EditText node1;
    EditText node2;
    LinearLayout infoContainer;

    private static final String LOG_TAG = "AndroidExample";
    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;
    int state = 0;
    int[][] prevMatrix;
    int[][] adjacencyMatrix;
    int[][] adjmatrix;
    int[][] matrix;
    int nodesNum = 0;
    int input1 = 0, input2 = 0;
    List<List<Integer>> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_input);
        textView = findViewById(R.id.textView2);
        infoContainer = findViewById(R.id.infoContainer);
        editMatrix = findViewById(R.id.editTextTextMultiLine);
        button = findViewById(R.id.button);
        node1 = findViewById(R.id.editTextNumberDecimal);
        node2 = findViewById(R.id.editTextNumberDecimal2);
        textViewDist = findViewById(R.id.dist);
        textViewShortPath = findViewById(R.id.path);
        textViewAllPaths = findViewById(R.id.path2);
        nodesNumberText = findViewById(R.id.numberOfNodes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem save = menu.findItem(R.id.action_save);
        save.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        Intent intent;
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_info:
                intent = new Intent(MatrixInput.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_help:
                intent = new Intent(MatrixInput.this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_main:
                intent = new Intent(MatrixInput.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_save:
               saveFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {

        String errorMessage = checkNodesNum();
        if (errorMessage == null) {
            errorMessage = checkMatrix();
        }
        if (errorMessage == null) {
            errorMessage = checkNodes();
        }
        if (errorMessage == null) {
            findPath();
        } else {
            textView.setText(errorMessage);
            textView.setTextColor(Color.parseColor("#ee3300"));
        }
    }
    private void saveFile(){

    }
    public void uploadFile(View view) {


//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        // Update with mime types
//        intent.setType("*/*");
//        // Update with additional mime types here using a String[].
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, 3);
//        // Only pick openable and local files. Theoretically we could pull files from google drive
//        // or other applications that have networked files, but that's unnecessary for this example.
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//        // REQUEST_CODE = <some-integer>
//        startActivityForResult(intent, 301);
    }


    private void findPath() {
        textView.setText("");
        Dijkstra alg = new Dijkstra(matrix, matrix.length);
        int dist = alg.dijkstra(input1, input2);
        if (dist != -1) {
            textViewDist.setText("Shortest distance = " + dist);
            int[] path = alg.getShortestPath();
            String pathText = "Shortest path : " + path[0];
            for (int i = 1; i < path.length; i++) {
                pathText += (" -> " + path[i]);
            }
            textViewShortPath.setText(pathText);
            AllPaths allPaths = new AllPaths();
            String allPathText = "All paths : \n";
            paths = allPaths.allPathsSourceTarget(adjmatrix, input1, input2);
            if (input1 != input2) {
                for (List<Integer> p : paths) {
                    for (Integer node : p) {
                        if (node == input2)
                            allPathText += node.toString() + "\n";
                        else allPathText += (node + " -> ");
                    }
                }
            } else {
                allPathText += (input1 + " -> " + input2);
            }

            textViewAllPaths.setText(allPathText);
        } else {
            textViewDist.setText("There is no path");
            paths = new ArrayList<>();
        }
        Path path = new Path();
        path.setAdjMatrix(adjmatrix);
        path.setPath(paths);
        path.setNodesNum(nodesNum);
        Intent intent = new Intent(MatrixInput.this, MatrixCalc.class);
        intent.putExtra("path", path);
        startActivity(intent);

    }

    private String checkNodesNum() {
        String errorMessage = null;
        try {
            nodesNum = Integer.parseInt(nodesNumberText.getText().toString());
        } catch (Exception e) {
            errorMessage = "Empty nodes. Enter the nodes for finding the path";
            return errorMessage;
        }
        if (nodesNum < 1) errorMessage = "Wrong nodes number";
        return errorMessage;
    }

    private String checkMatrix() {
        String errorMessage = null;
        textViewDist.setText("");
        textViewShortPath.setText("");
        String rawMatrixString = editMatrix.getText().toString().trim().replaceAll("[ ]", "");
        if (rawMatrixString.contains(",,")) {
            errorMessage = "skiped number betwin ',' and ','";
            matrix = null;
            adjacencyMatrix = null;
            return errorMessage;
        }
        String[] text = rawMatrixString.split("\n");
        int width = text[0].split(",").length;
        int height = text.length;
        if (width != 3) {
            errorMessage = "wrong size of matrix!\nwidth must be equals 3";
            matrix = null;
            adjacencyMatrix = null;
            return errorMessage;
        }
        for (int i = 0; i < text.length; i++) {
            String[] matrixRow = text[i].split(",");
            if (matrixRow.length != width) {
                errorMessage = "wrong matrix format! row [" + i + "] length:" + matrixRow.length + " but must be : " + width;
                matrix = null;
                adjacencyMatrix = null;
                return errorMessage;
            }
            for (int j = 0; j < text[i].length(); j++) {
                char t = text[i].charAt(j);
                if (!Character.isDigit(t) && t != ',') {
                    errorMessage = "wrong character! line[" + i + "] position is string line[" + j + "]";
                    adjacencyMatrix = null;
                    matrix = null;
                    return errorMessage;
                }
            }
        }

        adjacencyMatrix = new int[height][width];
        adjmatrix = new int[height][width - 1];
        matrix = new int[nodesNum][nodesNum];
        for (int i = 0; i < height; i++) {
            String[] numbers = text[i].split(",");
            for (int j = 0; j < width; j++) {
                if (Integer.parseInt(numbers[j]) < 1) {
                    adjacencyMatrix = null;
                    matrix = null;
                    errorMessage = "Zero value in matrix ";
                    return errorMessage;
                }
                adjacencyMatrix[i][j] = Integer.parseInt(numbers[j]);
                if (adjacencyMatrix[i][0] > nodesNum || adjacencyMatrix[i][1] > nodesNum) {
                    adjacencyMatrix = null;
                    matrix = null;
                    errorMessage = "Not existing node";
                    return errorMessage;
                }
            }
            matrix[adjacencyMatrix[i][0] - 1][adjacencyMatrix[i][1] - 1] = adjacencyMatrix[i][2];
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                adjmatrix[i][j] = adjacencyMatrix[i][j];
            }
        }

        return errorMessage;
    }

    private String checkNodes() {
        String errorMessage = null;
        textViewDist.setText("");
        textViewShortPath.setText("");
        textViewAllPaths.setText("");
        try {
            input1 = Integer.parseInt(node1.getText().toString());
            input2 = Integer.parseInt(node2.getText().toString());
        } catch (Exception e) {
            errorMessage = "Empty nodes. Enter the nodes for finding the path";
            return errorMessage;
        }
        if (input1 < 1 || input1 > matrix.length || input2 < 1 || input2 > matrix.length) {
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