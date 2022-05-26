package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.pricingengine.yieldcurve.YieldCurve;

import java.util.Map;

public record PriceCollection(Map<String, PriceCollectionItem> instrumentIdToPriceCollectionItem,
                              YieldCurve yieldCurve) {

    public PriceCollectionItem getPriceCollectionFromInstrumentId(String instrumentId) {
        return instrumentIdToPriceCollectionItem.get(instrumentId);
    }

    @Override
    public Map<String, PriceCollectionItem> instrumentIdToPriceCollectionItem() {
        return instrumentIdToPriceCollectionItem;
    }

    @Override
    public YieldCurve yieldCurve() {
        return yieldCurve;
    }
}
