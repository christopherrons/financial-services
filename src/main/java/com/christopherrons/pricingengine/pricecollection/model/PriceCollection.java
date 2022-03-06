package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.pricingengine.yieldcurve.YieldCurve;

import java.util.ArrayList;
import java.util.List;

public class PriceCollection {

    private final List<PriceCollectionItem> priceCollectionItems = new ArrayList<>();
    private YieldCurve yieldCurve;

    public YieldCurve getYieldCurve() {
        return yieldCurve;
    }

    public void setYieldCurve(YieldCurve yieldCurve) {
        this.yieldCurve = yieldCurve;
    }

    public List<PriceCollectionItem> getPriceCollectionItems() {
        return priceCollectionItems;
    }
}
