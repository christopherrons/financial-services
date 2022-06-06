package com.christopherrons.common.model.pricing;

import com.christopherrons.common.model.refdata.YieldRefData;
import math.interpolation.CubicSplintInterpolationTestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static testutils.TestAssertionUtils.assertDouble;

class YieldCurveTest {

    @Test
    void testYieldCurve5Points() {
        LocalDate startDate = LocalDate.parse("2000-01-01");
        List<LocalDate> maturityDates = new ArrayList<>();
        maturityDates.add(startDate.plusDays((long) YieldRefData.DAYS_PER_YEAR));
        maturityDates.add(startDate.plusDays((long) YieldRefData.DAYS_PER_YEAR * 2));
        maturityDates.add(startDate.plusDays((long) YieldRefData.DAYS_PER_YEAR * 3));
        maturityDates.add(startDate.plusDays((long) YieldRefData.DAYS_PER_YEAR * 4));
        maturityDates.add(startDate.plusDays((long) YieldRefData.DAYS_PER_YEAR * 5));
        double[] yields = new double[]{1, 5, 4, 3, 2};
        YieldCurve curve = new YieldCurve(new YieldRefData(LocalDate.parse("2000-01-01"), maturityDates, yields));
        for (int i = 0; i < 10000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(1, 3);
            assertDouble(CubicSplintInterpolationTestUtils.getFunctionValue5Points(x), curve.getYield(x), 0.0000001);
        }
    }
}