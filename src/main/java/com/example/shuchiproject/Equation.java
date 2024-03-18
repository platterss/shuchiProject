package com.example.shuchiproject;

public class Equation {
    private int xSquared;
    private int x;
    private int constant;

    public Equation(int xSquared, int x, int constant) {
        this.xSquared = xSquared;
        this.x = x;
        this.constant = constant;
    }

    public int getA() {
        return xSquared;
    }

    public int getB() {
        return x;
    }

    public int getC() {
        return constant;
    }

    public double getVertexX() {
        return (double) (-1 * x) / (2 * xSquared);
    }

    public double getValue(double x) {
        return xSquared * Math.pow(x, 2) + this.x * x + constant;
    }

    public double getFocusY() {
        return getValue(getVertexX()) + (1.0 / 4 * xSquared);
    }

    public String toString() {
        return xSquared + "x^2 + " + x + "x + " + constant;
    }
}