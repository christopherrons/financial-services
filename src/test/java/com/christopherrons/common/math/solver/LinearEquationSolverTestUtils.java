package com.christopherrons.common.math.solver;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class LinearEquationSolverTestUtils {

    public static RealVector getConstantVector() {
        double[] constant = {1, 5, 5, 4, 0, 0, 0, 0};
        return new ArrayRealVector(constant);
    }

    public static RealMatrix getCoefficientMatrix() {
        double[][] coefficients = {
                {1.0, 1.0, 1.0, 1.0, 0, 0, 0, 0},
                {8.0, 4.0, 2.0, 1.0, 0, 0, 0, 0},
                {0, 0, 0, 0, 8.0, 4.0, 2.0, 1.0},
                {0, 0, 0, 0, 27.0, 9.0, 3.0, 1.0},
                {12.0, 4.0, 1.0, 0, -12.0, -4.0, -1.0, 0},
                {12.0, 2.0, 0, 0, -12.0, -2.0, 0, 0},
                {6.0, 2.0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 18.0, 2.0, 0, 0}
        };
        return new Array2DRowRealMatrix(coefficients);
    }
}
