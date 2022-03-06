package com.christopherrons.common.math.interpolation;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static com.christopherrons.TestAssertionUtils.assertDouble;
import static com.christopherrons.common.math.interpolation.CubicSplintInterpolationTestUtils.*;

class CubicSplineInterpolationTest {

    @Test
    void testCubicSplineInterpolation3Points() {
        CubicSplineInterpolation interpolation = new CubicSplineInterpolation(get3YieldPoints());
        for (int i = 0; i < 10000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(1, 3);
            assertDouble(getFunctionValue3Points(x), interpolation.getFunctionValue(x), 0.0000001);
        }
    }


    @Test
    void testCubicSplineInterpolation5Points() {
        CubicSplineInterpolation interpolation = new CubicSplineInterpolation(get5YieldPoints());
        for (int i = 0; i < 10000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(1, 3);
            assertDouble(getFunctionValue5Points(x), interpolation.getFunctionValue(x), 0.0000001);
        }
    }
}