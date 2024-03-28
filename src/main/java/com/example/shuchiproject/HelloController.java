package com.example.shuchiproject;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    public LineChart<Number, Number> lineGraph;
    @FXML
    private Label welcomeText;
    @FXML
    private Button computeButton;
    @FXML
    private TextArea derivativeText;
    @FXML
    private TextField xSquaredText;
    @FXML
    private TextField xText;
    @FXML
    private TextField constantText;

    @FXML
    protected void onComputeButtonClick() {
        if (validateInteger(xSquaredText.getText()) && validateInteger(xText.getText()) && validateInteger(constantText.getText())) {
            lineGraph.getData().clear();

            int a = Integer.parseInt(xSquaredText.getText());
            int b = Integer.parseInt(xText.getText());
            int c = Integer.parseInt(constantText.getText());

            Equation equation = new Equation(a, b, c);

            String vertex = "(" + equation.getVertexX() + ", " + equation.getValue(equation.getVertexX()) + ")";
            String focus = "(" + equation.getVertexX() + ", " + equation.getFocusY() + ")";

            derivativeText.setText("Vertex: " + vertex + "\nFocus: " + focus);

            Graph graph = new Graph(lineGraph);
            graph.plotEquation(equation.getA(), equation.getB(), equation.getC());
            graph.plotPoint(equation.getVertexX(), equation.getFocusY());
        } else {
            derivativeText.setText("Please enter valid integers for a, b, and c.");
        }
    }

    private boolean validateInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
