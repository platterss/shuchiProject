package com.example.shuchiproject;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Graph {
    private LineChart<Number, Number> lineChart;

    public Graph(LineChart<Number, Number> lineChart) {
        this.lineChart = lineChart;
    }

    public void plotEquation(int a, int b) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (double x = -10; x <= 10; x += 0.1) {
            double y = a * x + b;
            series.getData().add(new XYChart.Data<>(x, y));
        }

        lineChart.getData().add(series);
    }

    public void plotEquation(int a, int b, int c) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (double x = -10; x <= 10; x += 0.1) {
            double y = a * Math.pow(x, 2) + b * x + c;
            series.getData().add(new XYChart.Data<>(x, y));
        }

        lineChart.getData().add(series);
    }

    public void plotPoint(double x, double y) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(x, y));

        lineChart.getData().add(series);
    }
}
