package com.christopherrons;

import org.junit.jupiter.api.Assertions;

public class TestAssertionUtils {

    public static void assertDoubleArray(final double[] expected, final double[] actual, final double delta) {
        for (int i = 0; i < expected.length; i++) {
            assertDouble(expected[i], actual[i], delta);
        }
    }

    public static void assertDouble(final double expected, final double acutal, final double delta) {
        Assertions.assertEquals(expected, acutal, delta);
    }
}
