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

    public String toString() {
        return xSquared + "x^2 + " + x + "x + " + constant;
    }
}

class Derivative extends Equation {
    private int derivativeX;
    private int derivativeConstant;

    public Derivative(int xSquared, int x, int constant) {
        super(xSquared, x, constant);
        derivativeX = xSquared * 2;
        derivativeConstant = x;
    }

    public String toString() {
        return derivativeX + "x + " + derivativeConstant;
    }

    public int getA() {
        return derivativeX;
    }

    public int getB() {
        return derivativeConstant;
    }
}