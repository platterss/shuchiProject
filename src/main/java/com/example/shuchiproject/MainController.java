package com.example.shuchiproject;

import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.chart.NumberAxis;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ComboBox dependentVarBox;
    public TextField squaredCoefficientInput, coefficientInput, constantInput, vertexOutput, focusOutput, directrixOutput, derivativeOutput;
    public Label independentSquaredTxt, independentTxt, promptTxt;
    public Button computeBtn;
    public LineChart graph;
    public NumberAxis xAxis, yAxis;

    private double xAxisLowerBound;
    private double xAxisUpperBound;
    private double yAxisLowerBound;
    private double yAxisUpperBound;

    private final double minXAxisBound = -1000;
    private final double maxXAxisBound = 1000;
    private final double minYAxisBound = -1000;
    private final double maxYAxisBound = 1000;

    private Point2D dragAnchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dependentVarBox.getItems().addAll("y", "x");

        dependentVarBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.equals("y")) {
                independentSquaredTxt.setText("x²");
                independentTxt.setText("x");
            } else if (newVal.equals("x")) {
                independentSquaredTxt.setText("y²");
                independentTxt.setText("y");
            }
        });

        dependentVarBox.getSelectionModel().selectFirst();
        setupAxes();
        setupChartInteractions();
    }

    public void compute() {
        double a;
        double b;
        double c;

        try {
            a = Double.parseDouble(squaredCoefficientInput.getText());
            b = Double.parseDouble(coefficientInput.getText());
            c = Double.parseDouble(constantInput.getText());

            promptTxt.setText("Enter a quadratic!");

            Quadratic quadratic = new Quadratic(((String) dependentVarBox.getValue()).charAt(0), a, b, c);

            vertexOutput.setText(quadratic.getVertex());
            focusOutput.setText(quadratic.getFocus());
            directrixOutput.setText(quadratic.getDirectrix());
            derivativeOutput.setText(quadratic.getDerivative());

            graph.getData().clear();

            plotParabola(quadratic.getA(), quadratic.getB(), quadratic.getC(), !quadratic.isDirectrixHorizontal(), "Quadratic");
            plotPoint(quadratic.getH(), quadratic.getK(), "Vertex");
            plotPoint(quadratic.getFocusX(), quadratic.getFocusY(), "Focus");
            plotLine(quadratic.getDirectrixValue(), quadratic.isDirectrixHorizontal(), "Directrix");
            plotLine(quadratic.getDerivativeA(), quadratic.getDerivativeB(), "Derivative");

        } catch (Exception e) {
            promptTxt.setText("Please enter valid numbers.");
        }
    }

    private void setupAxes() {
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-10);
        xAxis.setUpperBound(10);
        xAxis.setTickUnit(1);
        xAxis.setLabel("X Axis");

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-10);
        yAxis.setUpperBound(10);
        yAxis.setTickUnit(1);
        yAxis.setLabel("Y Axis");
    }

    private void setupChartInteractions() {
        xAxisLowerBound = xAxis.getLowerBound();
        xAxisUpperBound = xAxis.getUpperBound();
        yAxisLowerBound = yAxis.getLowerBound();
        yAxisUpperBound = yAxis.getUpperBound();

        graph.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();

            double zoomFactor = 0.1;

            double scaleX = (xAxisUpperBound - xAxisLowerBound) * zoomFactor;
            double scaleY = (yAxisUpperBound - yAxisLowerBound) * zoomFactor;

            if (deltaY < 0) {
                scaleX = -scaleX;
                scaleY = -scaleY;
            }

            double newLowerBoundX = xAxisLowerBound + scaleX;
            double newUpperBoundX = xAxisUpperBound - scaleX;
            double newLowerBoundY = yAxisLowerBound + scaleY;
            double newUpperBoundY = yAxisUpperBound - scaleY;

            newLowerBoundX = Math.max(newLowerBoundX, minXAxisBound);
            newUpperBoundX = Math.min(newUpperBoundX, maxXAxisBound);
            newLowerBoundY = Math.max(newLowerBoundY, minYAxisBound);
            newUpperBoundY = Math.min(newUpperBoundY, maxYAxisBound);

            if (newLowerBoundX < newUpperBoundX && newLowerBoundY < newUpperBoundY) {
                xAxisLowerBound = newLowerBoundX;
                xAxisUpperBound = newUpperBoundX;
                yAxisLowerBound = newLowerBoundY;
                yAxisUpperBound = newUpperBoundY;

                xAxis.setLowerBound(xAxisLowerBound);
                xAxis.setUpperBound(xAxisUpperBound);
                yAxis.setLowerBound(yAxisLowerBound);
                yAxis.setUpperBound(yAxisUpperBound);
            }

            event.consume();
        });


        graph.setOnZoom((ZoomEvent event) -> {
            double zoomFactor = 1 / event.getZoomFactor();

            zoomFactor = zoomFactor > 1 ? 1.05 : 0.95;

            double scaleX = (xAxisUpperBound - xAxisLowerBound) * (1 - zoomFactor);
            double scaleY = (yAxisUpperBound - yAxisLowerBound) * (1 - zoomFactor);

            double newLowerBoundX = xAxisLowerBound + scaleX / 2;
            double newUpperBoundX = xAxisUpperBound - scaleX / 2;
            double newLowerBoundY = yAxisLowerBound + scaleY / 2;
            double newUpperBoundY = yAxisUpperBound - scaleY / 2;

            if (newLowerBoundX >= minXAxisBound && newUpperBoundX <= maxXAxisBound) {
                xAxis.setLowerBound(newLowerBoundX);
                xAxis.setUpperBound(newUpperBoundX);
                xAxisLowerBound = newLowerBoundX;
                xAxisUpperBound = newUpperBoundX;
            }

            if (newLowerBoundY >= minYAxisBound && newUpperBoundY <= maxYAxisBound) {
                yAxis.setLowerBound(newLowerBoundY);
                yAxis.setUpperBound(newUpperBoundY);
                yAxisLowerBound = newLowerBoundY;
                yAxisUpperBound = newUpperBoundY;
            }

            event.consume();
        });

        graph.setOnMousePressed((MouseEvent event) -> {
            dragAnchor = new Point2D(event.getX(), event.getY());
        });

        graph.setOnMouseDragged((MouseEvent event) -> {
            double deltaX = dragAnchor.getX() - event.getX();
            double deltaY = event.getY() - dragAnchor.getY();
            double scaleX = (xAxisUpperBound - xAxisLowerBound) / graph.getWidth();
            double scaleY = (yAxisUpperBound - yAxisLowerBound) / graph.getHeight();

            double proposedXAxisLowerBound = xAxisLowerBound + deltaX * scaleX;
            double proposedXAxisUpperBound = xAxisUpperBound + deltaX * scaleX;
            double proposedYAxisLowerBound = yAxisLowerBound + deltaY * scaleY;
            double proposedYAxisUpperBound = yAxisUpperBound + deltaY * scaleY;

            if (proposedXAxisLowerBound >= minXAxisBound && proposedXAxisUpperBound <= maxXAxisBound) {
                xAxisLowerBound = proposedXAxisLowerBound;
                xAxisUpperBound = proposedXAxisUpperBound;
                xAxis.setLowerBound(xAxisLowerBound);
                xAxis.setUpperBound(xAxisUpperBound);
            }

            if (proposedYAxisLowerBound >= minYAxisBound && proposedYAxisUpperBound <= maxYAxisBound) {
                yAxisLowerBound = proposedYAxisLowerBound;
                yAxisUpperBound = proposedYAxisUpperBound;
                yAxis.setLowerBound(yAxisLowerBound);
                yAxis.setUpperBound(yAxisUpperBound);
            }

            dragAnchor = new Point2D(event.getX(), event.getY());
        });

    }

    private void plotParabola(double a, double b, double c, boolean isHorizontal, String name) {
        XYChart.Series<Number, Number> parabolaSeries = new XYChart.Series<>();
        parabolaSeries.setName(name);

        double min = isHorizontal ? yAxisLowerBound : xAxisLowerBound;
        double max = isHorizontal ? yAxisUpperBound : xAxisUpperBound;
        double step = (max - min) / 400;

        for (double x = min; x <= max; x += step) {
            double y;
            if (isHorizontal) {
                y = (a * x * x) + (b * x) + c;
                parabolaSeries.getData().add(new XYChart.Data<>(y, x));
            } else {
                y = (a * x * x) + (b * x) + c;
                parabolaSeries.getData().add(new XYChart.Data<>(x, y));
            }
        }

        graph.getData().add(parabolaSeries);
    }


    public void plotPoint(double x, double y, String name) {
        XYChart.Series<Number, Number> pointSeries = new XYChart.Series<>();
        pointSeries.setName(name);
        pointSeries.getData().add(new XYChart.Data<>(x, y));

        graph.getData().add(pointSeries);
    }

    public void plotLine(double slope, double yIntercept, String name) {
        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName(name);

        double startX = minXAxisBound;
        double endX = maxXAxisBound;
        double startY = slope * startX + yIntercept;
        double endY = slope * endX + yIntercept;

        lineSeries.getData().add(new XYChart.Data<>(startX, startY));
        lineSeries.getData().add(new XYChart.Data<>(endX, endY));

        graph.getData().add(lineSeries);
    }

    public void plotLine(double value, boolean isHorizontal, String name) {
        XYChart.Series<Number, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName(name);

        if (isHorizontal) {
            lineSeries.getData().add(new XYChart.Data<>(minXAxisBound, value));
            lineSeries.getData().add(new XYChart.Data<>(maxXAxisBound, value));
        } else {
            lineSeries.getData().add(new XYChart.Data<>(value, minYAxisBound));
            lineSeries.getData().add(new XYChart.Data<>(value, maxYAxisBound));
        }

        graph.getData().add(lineSeries);
    }
}