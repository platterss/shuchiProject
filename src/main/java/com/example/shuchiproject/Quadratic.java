package com.example.shuchiproject;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Quadratic {
    private final char DEPENDENT_VAR;
    private final double A;
    private final double B;
    private final double C;

    public Quadratic(char dependentVar, double a, double b, double c) {
        this.DEPENDENT_VAR = dependentVar;
        this.A = a;
        this.B = b;
        this.C = c;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

    public double getH() {
        if (DEPENDENT_VAR == 'y') {
            return round((-1 * (B / (2 * A))), 3);
        } else {
            return round(C - (Math.pow(B, 2) / (4 * A)), 3);
        }
    }

    public double getK() {
        if (DEPENDENT_VAR == 'x') {
            return round((-1 * (B / (2 * A))), 3);
        } else {
            return round(C - (Math.pow(B, 2) / (4 * A)), 3);
        }
    }

    public String getVertex() {
        return "(" + getH() + ", " + getK() + ")";
    }

    public double getFocusX() {
        double x;

        if (DEPENDENT_VAR == 'y') {
            x = getH();
        } else {
            x = round(getH() + (1 / (4 * A)), 3);
        }

        return x;
    }

    public double getFocusY() {
        double y;

        if (DEPENDENT_VAR == 'y') {
            y = round(getK() + (1 / (4 * A)), 3);
        } else {
            y = getK();
        }

        return y;
    }

    public String getFocus() {
        return "(" + getFocusX() + ", " + getFocusY() + ")";
    }

    public double getDirectrixValue() {
        double result;

        if (DEPENDENT_VAR == 'y') {
            result = round(getK() - (1 / (4 * A)), 3);
        } else {
            result = round(getH() - (1 / (4 * A)), 3);
        }

        return result;
    }

    public boolean isDirectrixHorizontal() {
        return DEPENDENT_VAR == 'y';
    }

    public String getDirectrix() {
        return DEPENDENT_VAR + " = " + getDirectrixValue();
    }

    public double getDerivativeA() {
        return 2 * A;
    }

    public double getDerivativeB() {
        return B;
    }

    public String getDerivative() {
        char independentVar;
        char operation = '+';

        if (DEPENDENT_VAR == 'y') {
            independentVar = 'x';
        } else {
            independentVar = 'y';
        }

        if (getDerivativeB() < 0) {
            operation = '-';
        }

        if (B == 0) {
            return DEPENDENT_VAR + " = " + getDerivativeA() + independentVar;
        }

        return DEPENDENT_VAR + " = " + getDerivativeA() + independentVar + " " + operation + " " + Math.abs(getDerivativeB());
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
