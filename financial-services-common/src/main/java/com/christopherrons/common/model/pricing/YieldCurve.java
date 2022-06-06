package com.christopherrons.common.model.pricing;

import com.christopherrons.common.math.api.CartesianPoint2d;
import com.christopherrons.common.math.interpolation.CubicSplineInterpolation;
import com.christopherrons.common.model.refdata.YieldRefData;

import java.util.ArrayList;
import java.util.List;

public class YieldCurve {

    public final YieldRefData yieldRefData;
    private final CubicSplineInterpolation splineInterpolation;
    private final List<CartesianPoint2d> yieldPoints = new ArrayList<>();

    public YieldCurve(YieldRefData yieldRefData) {
        this.yieldRefData = yieldRefData;
        createYieldPoints(yieldRefData.getMaturities(), yieldRefData.getYields());
        this.splineInterpolation = new CubicSplineInterpolation(yieldPoints);
    }

    private void createYieldPoints(double[] maturities, double[] yields) {
        for (int i = 0; i < maturities.length; i++) {
            yieldPoints.add(new YieldPoint(maturities[i], yields[i]));
        }
        yieldPoints.sort((CartesianPoint2d p1, CartesianPoint2d p2) -> p1.getValueX() > p2.getValueX() ? 1 : -1);
    }

    public double getYield(final double maturity) {
        return splineInterpolation.getFunctionValue(maturity);
    }

    public double[] getMaturities() {
        return yieldRefData.getMaturities();
    }

    public double[] getYields() {
        return yieldRefData.getYields();
    }

    public List<CartesianPoint2d> getYieldPoints() {
        return yieldPoints;
    }

    public double getStartBoundaryMaturity() {
        return splineInterpolation.getStartBoundaryX();
    }

    public double getStartBoundaryYield() {
        return splineInterpolation.getStartBoundaryY();
    }

    public double getEndBoundaryMaturity() {
        return splineInterpolation.getEndBoundaryX();
    }

    public double getEndBoundaryYield() {
        return splineInterpolation.getEndBoundaryY();
    }
}
