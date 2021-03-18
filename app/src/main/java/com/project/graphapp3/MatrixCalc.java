package com.project.graphapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MatrixCalc extends AppCompatActivity {
LineGraphSeries<DataPoint>series1=new LineGraphSeries<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_calc);

        double x,y;
        x=0;
        GraphView graphView = (GraphView)findViewById(R.id.graph);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        int n = 20;
        float r = 10;
        for (int  i = 0;  i < n;  i++) {
         x=Math.cos(360.0/n)*r;
         y=Math.sin(360.0/n)*r;
         series1.appendData(new DataPoint(x,y), true,100);
        }
        graphView.addSeries(series1);
    }




}