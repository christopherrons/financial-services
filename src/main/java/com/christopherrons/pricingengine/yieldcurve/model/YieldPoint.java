package com.christopherrons.pricingengine.yieldcurve.model;

import com.christopherrons.common.math.api.CartesianPoint2d;

public class YieldPoint implements CartesianPoint2d {

    private final double maturity;
    private final double yieldInDecimalForm;

    public YieldPoint(double maturity, double yieldInDecimalForm) {
        this.maturity = maturity;
        this.yieldInDecimalForm = yieldInDecimalForm;
    }

    @Override
    public double getValueX() {
        return this.maturity;
    }

    @Override
    public double getValueY() {
        return this.yieldInDecimalForm;
    }
}
