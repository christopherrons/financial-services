package com.christopherrons.common.math.solver;

import org.apache.commons.math3.linear.*;

public class LinearEquationSolver {

    private LinearEquationSolver() {
        throw new IllegalStateException("Utility class");
    }

    public static RealVector solveSystem(final double[][] coefficients, final double[] constants) {
        return solveSystem(new Array2DRowRealMatrix(coefficients), new ArrayRealVector(constants));
    }

    public static RealVector solveSystem(final RealMatrix coefficients, final RealVector constants) {
        final DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        return solver.solve(constants);
    }
}
