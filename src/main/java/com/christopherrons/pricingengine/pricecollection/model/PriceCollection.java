package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.pricingengine.yieldcurve.YieldCurve;

import java.util.List;

public record PriceCollection(List<PriceCollectionItem> priceCollectionItems, YieldCurve yieldCurve) {
}
