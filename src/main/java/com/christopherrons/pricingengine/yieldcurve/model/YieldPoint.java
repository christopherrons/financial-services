package com.christopherrons.pricingengine.yieldcurve.model;

import com.christopherrons.common.math.api.CartesianPoint2d;

public record YieldPoint(double maturity, double yieldInDecimalForm) implements CartesianPoint2d {

    @Override
    public double getValueX() {
        return this.maturity;
    }

    @Override
    public double getValueY() {
        return this.yieldInDecimalForm;
    }
}
