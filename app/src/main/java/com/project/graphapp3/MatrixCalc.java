package com.project.graphapp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.project.graphapp3.model.GraphDot;
import com.project.graphapp3.model.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixCalc extends AppCompatActivity {
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
    Map<Integer, GraphDot> dots;
    List<List<Integer>> paths;
    int[][] adjMatrix;
    float radius = 25;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawGrafView(this));
        Intent intent = getIntent();
        Path path = (Path) intent.getExtras().get("path");

        int testArray[][];

        testArray = path.getAdjMatrix();
        paths = path.getPath();
        dots = new HashMap<>();
        calculateDots(dots, testArray, path.getNodesNum());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        Intent intent;
        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_info:
                intent = new Intent(MatrixCalc.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_main:
                intent = new Intent(MatrixCalc.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void drawArrow1(Canvas canvas, Paint paint, float x, float y, float x1, float y1) {
        double degree = calculateDegree(x, x1, y, y1);
        x1 = (float) (x1 + ((50) * Math.cos(Math.toRadians((degree) + 90))));
        y1 = (float) (y1 + ((50) * Math.sin(Math.toRadians(((degree) + 90)))));

        float endX1 = (float) (x1 + ((50) * Math.cos(Math.toRadians((degree - 30) + 90))));
        float endY1 = (float) (y1 + ((50) * Math.sin(Math.toRadians(((degree - 30) + 90)))));

        float endX2 = (float) (x1 + ((50) * Math.cos(Math.toRadians((degree - 60) + 180))));
        float endY2 = (float) (y1 + ((50) * Math.sin(Math.toRadians(((degree - 60) + 180)))));

        canvas.drawLine(x1, y1, endX1, endY1, paint);
        canvas.drawLine(x1, y1, endX2, endY2, paint);
    }

    public double calculateDegree(float x1, float x2, float y1, float y2) {
        float startRadians = (float) Math.atan((y2 - y1) / (x2 - x1));
        startRadians += ((x2 >= x1) ? 90 : -90) * Math.PI / 180;
        return Math.toDegrees(startRadians);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculateDots(Map<Integer, GraphDot> dots, int[][] dataArray, int unicDotAmount) {
        float radius = 430;
        int textDistance = 60;
        for (int i = 0; i < unicDotAmount; i++) {
            GraphDot dot = new GraphDot();
            dot.setName(i + 1);
            dots.put(i + 1, dot);
            float x = (float) (Math.cos(i * 2 * Math.PI / unicDotAmount) * (radius) + radius + 100);
            float y = (float) (Math.sin(i * 2 * Math.PI / unicDotAmount) * (radius) + radius + 100);
            float textX = (float) (Math.cos(i * 2 * Math.PI / unicDotAmount) * (radius + textDistance) + radius + 100 + this.radius / 2.5);
            float textY = (float) (Math.sin(i * 2 * Math.PI / unicDotAmount) * (radius + textDistance) + radius + 100 + this.radius / 2.5);

            dot.setX(x);
            dot.setY(y);
            dot.setNameX(textX);
            dot.setNameY(textY);
        }

        for (int i = 0; i < dataArray.length; i++) {
            dots.get(dataArray[i][0]).getEdges().add(dataArray[i][1]);
        }
    }

    public class DrawGrafView extends View {

        public DrawGrafView(Context context) {
            super(context);
            // paintDots.setColor(Color.BLUE);
            paintDots.setColor(Color.parseColor("#e3f51b"));
            paintGraf.setColor(Color.BLACK);
            paintGraf.setStrokeWidth(20);
            paintGraf2.setStrokeWidth(13);
            paintGraf2.setColor(Color.BLACK);
            paintText.setTextSize(50);
            paintText.setTextAlign(Paint.Align.CENTER);
            paintText.setColor(Color.BLACK);
        }

        Paint paintDots = new Paint();
        Paint paintGraf = new Paint();
        Paint paintGraf2 = new Paint();
        Paint paintText = new Paint();

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            drawGraf(dots, canvas);
            drawPaths(dots, paths, canvas);
            drawPiks(dots, canvas);
        }

        private void drawPaths(Map<Integer, GraphDot> dots, List<List<Integer>> paths, Canvas canvas) {
            Paint paintPaths = new Paint();
            for (List<Integer> path : paths) {
                paintPaths.setStrokeWidth(5);
                paintPaths.setColor(Color.argb(255, (int) (Math.random() * 125 + 125), (int) (Math.random() * 125 + 125), (int) (Math.random() * 125 + 125)));
                //paintPaths.setColor(Color.argb(255, (int) (Math.random() * 125 + 125), (int) (Math.random() * 125 + 125), (int) (Math.random() * 125 + 125)));
                for (int i = 1; i < path.size(); i++) {
                    GraphDot dot1 = dots.get(path.get(i - 1));
                    GraphDot dot2 = dots.get(path.get(i));
                    canvas.drawLine(dot1.getX(), dot1.getY(), dot2.getX(), dot2.getY(), paintPaths);
                    drawArrow1(canvas, paintPaths, dot1.getX(), dot1.getY(), dot2.getX(), dot2.getY());
                }
            }
        }

        private void drawPiks(Map<Integer, GraphDot> dots, Canvas canvas) {
            for (GraphDot dot : dots.values()) {
                canvas.drawCircle(dot.getX(), dot.getY(), radius, paintDots);
            }
        }

        private void drawGraf(Map<Integer, GraphDot> dots, Canvas canvas) {
            for (GraphDot dot : dots.values()) {
                canvas.drawText("" + dot.getName(), dot.getNameX(), dot.getNameY(), paintText);
                for (Integer n : dot.getEdges()) {
                    GraphDot anotherDot = dots.get(n);
                    try {
                        canvas.drawLine(dot.getX(), dot.getY(), anotherDot.getX(), anotherDot.getY(), paintGraf);
                    } catch (Exception e) {
                        System.out.println();
                    }
                    drawArrow1(canvas, paintGraf2, dot.getX(), dot.getY(), anotherDot.getX(), anotherDot.getY());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}