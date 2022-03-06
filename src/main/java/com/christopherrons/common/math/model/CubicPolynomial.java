package com.christopherrons.common.math.model;

import static java.lang.Math.pow;

public class CubicPolynomial {

    public static final int NR_OF_COEFFICIENTS_IN_POLYNOMIAL = 4;
    private final double startBoundaryPointX;
    private final double endBoundaryPointX;
    private final double startBoundaryPointY;
    private final double endBoundaryPointY;
    private double firstCoefficient = 0;
    private double secondCoefficient = 0;
    private double thirdCoefficient = 0;
    private double fourthCoefficient = 0;

    public CubicPolynomial() {
        this(Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public CubicPolynomial(double startBoundaryPointX, double endBoundaryPointX, double startBoundaryPointY, double endBoundaryPointY) {
        this.startBoundaryPointX = startBoundaryPointX;
        this.endBoundaryPointX = endBoundaryPointX;
        this.startBoundaryPointY = startBoundaryPointY;
        this.endBoundaryPointY = endBoundaryPointY;
    }

    public double getFunctionValue(final double x) {
        return firstCoefficient * pow(x, 3) +
                secondCoefficient * pow(x, 2) +
                thirdCoefficient * x +
                fourthCoefficient;
    }

    public double getFirstDerivative(final double x) {
        return 3 * firstCoefficient * pow(x, 2) +
                2 * secondCoefficient * x +
                thirdCoefficient;
    }

    public double getSecondDerivative(final double x) {
        return 6 * firstCoefficient * x +
                2 * secondCoefficient;
    }

    public double getStartBoundaryPointX() {
        return startBoundaryPointX;
    }

    public double getEndBoundaryPointX() {
        return endBoundaryPointX;
    }

    public double getStartBoundaryPointY() {
        return startBoundaryPointY;
    }

    public double getEndBoundaryPointY() {
        return endBoundaryPointY;
    }

    public void setFirstCoefficient(double firstCoefficient) {
        this.firstCoefficient = firstCoefficient;
    }

    public void setSecondCoefficient(double secondCoefficient) {
        this.secondCoefficient = secondCoefficient;
    }

    public void setThirdCoefficient(double thirdCoefficient) {
        this.thirdCoefficient = thirdCoefficient;
    }

    public void setFourthCoefficient(double fourthCoefficient) {
        this.fourthCoefficient = fourthCoefficient;
    }
}
