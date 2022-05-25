package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.pricingengine.marginprice.model.MarginPrice;

import java.util.List;

public record PriceCollectionItem(String instrumentId, MarginPrice marginPrice, List<Double> historicalClosingPrices) {
}

