package com.christopherrons.common.math.solver;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Test;

import static com.christopherrons.common.math.solver.LinearEquationSolverTestUtils.getCoefficientMatrix;
import static com.christopherrons.common.math.solver.LinearEquationSolverTestUtils.getConstantVector;
import static testutils.TestAssertionUtils.assertDoubleArray;

class LinearEquationSolverTest {


    @Test
    void testLinearEquationSolver() {
        RealMatrix A = getCoefficientMatrix();
        RealVector b = getConstantVector();
        RealVector x = LinearEquationSolver.solveSystem(A, b);

        RealVector x_true = new ArrayRealVector(new double[]{-1.25, 3.75, 1.5, -3.0, 1.25, -11.25, 31.5, -23.0});

        assertDoubleArray(x_true.toArray(), x.toArray(), 0.000001);

    }


}