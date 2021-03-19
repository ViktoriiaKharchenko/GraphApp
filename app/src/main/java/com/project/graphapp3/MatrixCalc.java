package com.project.graphapp3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.project.graphapp3.model.GraphDot;
import com.project.graphapp3.model.Path;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixCalc extends AppCompatActivity {
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
    Map<Integer, GraphDot> dots;
    List<List<Integer>> paths;
    int [][] adjMatrix;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawGrafView(this));
        int testArray[][] = {
                {1, 2},
                {1, 8},
                {2, 3},
                {3, 1},
                {3, 9},
                {4, 3},
                {5, 6},
                {6, 3},
                {7, 8},
                {8, 10},
                {9, 11},
                {10, 9},
                {11, 12},
                {12, 1}
        };
        paths = List.of(
                List.of(1, 8, 10, 9),
                List.of(5, 6, 3, 1),
                List.of(2, 3, 1)
        );
//        Path path = new Path();
//        adjMatrix = path.getAdjMatrix();
//        paths = path.getPath();
     dots = new HashMap<>();
     calculateDots(dots, testArray);

    }

    private void drawArrow1(Canvas canvas, Paint paint, float x, float y, float x1, float y1) {
        double degree = calculateDegree(x, x1, y, y1);
        x1= (float) (x1 + ((50) * Math.cos(Math.toRadians((degree) + 90))));
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
        System.out.println("radian=====" + Math.toDegrees(startRadians));
        startRadians += ((x2 >= x1) ? 90 : -90) * Math.PI / 180;
        return Math.toDegrees(startRadians);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculateDots(Map<Integer, GraphDot> dots, int[][] dataArray) {
        float radius = 450;
        long unicDotAmount = Arrays.stream(dataArray)
                .map(x -> x[0])
                .distinct()
                .count();
        int textDistance = 50;
        int count = 0;
        for (int i = 0; i < dataArray.length; i++) {
            GraphDot dot = dots.get(dataArray[i][0]);
            if (dot == null) {
                dot = new GraphDot();
                dot.setName(dataArray[i][0]);
                dots.put(dataArray[i][0], dot);
                float x = (float) (Math.cos(count * 2 * Math.PI / unicDotAmount) * (radius) + radius + 100);
                float y = (float) (Math.sin(count * 2 * Math.PI / unicDotAmount) * (radius) + radius + 100);
                float textX = (float) (Math.cos(count * 2 * Math.PI / unicDotAmount) * (radius + textDistance) + radius + 100);
                float textY = (float) (Math.sin(count * 2 * Math.PI / unicDotAmount) * (radius + textDistance) + radius + 100);

                dot.setX(x);
                dot.setY(y);
                dot.setNameX(textX);
                dot.setNameY(textY);
                count++;
            }
            dot.getEdges().add(dataArray[i][1]);
        }
    }

    public class DrawGrafView extends View {
        public DrawGrafView(Context context) {
            super(context);
            paintDots.setColor(Color.BLUE);
            paintGraf.setColor(Color.BLACK);
            paintGraf.setStrokeWidth(24);
            paintText.setTextSize(32);
            paintText.setColor(Color.BLACK);
        }

        Paint paintDots = new Paint();
        Paint paintGraf = new Paint();
        Paint paintText = new Paint();

        @Override
        public void draw(Canvas canvas) {
            super.draw(canvas);
            drawGraf(dots, canvas);
            drawPaths(dots, paths, canvas);
            drawPiks(dots, canvas);
        }

        private void drawPaths(Map<Integer, GraphDot> dots, List<List<Integer>> paths, Canvas canvas) {
            Paint paintPaths =  new Paint();
            for (List<Integer> path : paths) {
                paintPaths.setStrokeWidth(5);
                paintPaths.setColor(Color.argb(255, (int)(Math.random()*125 + 125), (int)(Math.random()*125 + 125), (int)(Math.random()*125 + 125)));
                for (int i = 1; i < path.size(); i++) {
                    GraphDot dot1 = dots.get(path.get(i-1));
                    GraphDot dot2 = dots.get(path.get(i));
                    canvas.drawLine(dot1.getX(),dot1.getY(),dot2.getX(),dot2.getY(),paintPaths);
                    drawArrow1(canvas, paintPaths, dot1.getX(),dot1.getY(),dot2.getX(),dot2.getY());
                }
            }
        }

        private void drawPiks(Map<Integer, GraphDot> dots, Canvas canvas) {
            for (GraphDot dot : dots.values()) {
                canvas.drawCircle(dot.getX(), dot.getY(), 25, paintDots);
            }
        }

        private void drawGraf(Map<Integer, GraphDot> dots, Canvas canvas) {
            for (GraphDot dot : dots.values()) {
                canvas.drawText("" + dot.getName(), dot.getNameX(), dot.getNameY(), paintText);
                for (Integer n : dot.getEdges()) {
                    GraphDot anotherDot = dots.get(n);
                    canvas.drawLine(dot.getX(), dot.getY(), anotherDot.getX(), anotherDot.getY(), paintGraf);
                    drawArrow1(canvas, paintGraf, dot.getX(), dot.getY(), anotherDot.getX(), anotherDot.getY());
                }
            }
        }
    }


}