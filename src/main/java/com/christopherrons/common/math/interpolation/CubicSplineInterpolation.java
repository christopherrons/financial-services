package com.christopherrons.common.math.interpolation;

import com.christopherrons.common.math.api.CartesianPoint2d;
import com.christopherrons.common.math.model.CubicPolynomial;
import com.christopherrons.common.math.solver.LinearEquationSolver;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

public class CubicSplineInterpolation {

    private final List<CartesianPoint2d> points;
    private final List<CubicPolynomial> polynomials = new ArrayList<>();

    public CubicSplineInterpolation(List<CartesianPoint2d> points) {
        this.points = points;
        createPolynomials();
    }

    public double getFunctionValue(double x) {
        x = checkInputValue(x);
        for (CubicPolynomial polynomial : polynomials) {
            if (x < polynomial.getEndBoundaryPointX()) {
                return polynomial.getFunctionValue(x);
            }
        }
        return polynomials.get(polynomials.size() - 1).getFunctionValue(x);
    }

    public double getStartBoundaryX() {
        return polynomials.get(0).getStartBoundaryPointX();
    }

    public double getStartBoundaryY() {
        return polynomials.get(0).getStartBoundaryPointY();
    }

    public double getEndBoundaryX() {
        return polynomials.get(polynomials.size() - 1).getEndBoundaryPointX();
    }

    public double getEndBoundaryY() {
        return polynomials.get(polynomials.size() - 1).getEndBoundaryPointY();
    }

    private double checkInputValue(final double x) {
        if (x < getStartBoundaryX()) {
            return getStartBoundaryX();
        }
        return Math.min(x, getEndBoundaryX());
    }

    private void createPolynomials() {
        final int nrOfPolynomials = points.size() - 1;
        for (int i = 0; i < nrOfPolynomials; i++) {
            polynomials.add(new CubicPolynomial(
                            points.get(i).getValueX(),
                            points.get(i + 1).getValueX(),
                            points.get(i).getValueY(),
                            points.get(i + 1).getValueY()
                    )
            );
        }
        setPolynomialCoefficients();
    }

    private void setPolynomialCoefficients() {
        RealVector coefficients = getCoefficients();

        for (int i = 0; i < polynomials.size(); i++) {
            CubicPolynomial polynomial = polynomials.get(i);
            int rowIndex = i * 4;
            polynomial.setFirstCoefficient(coefficients.getEntry(rowIndex));
            polynomial.setSecondCoefficient(coefficients.getEntry(rowIndex + 1));
            polynomial.setThirdCoefficient(coefficients.getEntry(rowIndex + 2));
            polynomial.setFourthCoefficient(coefficients.getEntry(rowIndex + 3));
        }
    }

    private RealVector getCoefficients() {
        final CubicSplineLinearSystemBuilder linearSystemBuilder = new CubicSplineLinearSystemBuilder(polynomials);
        final RealMatrix A = linearSystemBuilder.createCoefficientMatrix();
        final RealVector b = linearSystemBuilder.createConstantVector();
        return LinearEquationSolver.solveSystem(A, b);
    }

}
