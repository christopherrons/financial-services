package math.interpolation;

import com.christopherrons.common.math.interpolation.CubicSplineInterpolation;
import org.junit.jupiter.api.Test;
import testutils.TestAssertionUtils;

import java.util.concurrent.ThreadLocalRandom;

class CubicSplineInterpolationTest {

    @Test
    void testCubicSplineInterpolation3Points() {
        CubicSplineInterpolation interpolation = new CubicSplineInterpolation(CubicSplintInterpolationTestUtils.get3YieldPoints());
        for (int i = 0; i < 10000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(1, 3);
            TestAssertionUtils.assertDouble(CubicSplintInterpolationTestUtils.getFunctionValue3Points(x), interpolation.getFunctionValue(x), 0.0000001);
        }
    }


    @Test
    void testCubicSplineInterpolation5Points() {
        CubicSplineInterpolation interpolation = new CubicSplineInterpolation(CubicSplintInterpolationTestUtils.get5YieldPoints());
        for (int i = 0; i < 10000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(1, 3);
            TestAssertionUtils.assertDouble(CubicSplintInterpolationTestUtils.getFunctionValue5Points(x), interpolation.getFunctionValue(x), 0.0000001);
        }
    }
}