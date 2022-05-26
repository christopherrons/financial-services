package testutils;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.jupiter.api.Assertions;

public class TestAssertionUtils {


    public static void assertDoubleMatrix(final RealMatrix expected, final RealMatrix actual, final double delta) {
        for (int column = 0; column < expected.getColumnDimension(); column++) {
            assertDoubleVector(expected.getColumnVector(column), actual.getColumnVector(column), delta);

        }
    }

    public static void assertDoubleVector(final RealVector expected, final RealVector actual, final double delta) {
        for (int i = 0; i < expected.getDimension(); i++) {
            assertDouble(expected.getEntry(i), actual.getEntry(i), delta);
        }
    }

    public static void assertDoubleArray(final double[] expected, final double[] actual, final double delta) {
        for (int i = 0; i < expected.length; i++) {
            assertDouble(expected[i], actual[i], delta);
        }
    }

    public static void assertDouble(final double expected, final double acutal, final double delta) {
        Assertions.assertEquals(expected, acutal, delta);
    }
}
