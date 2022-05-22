package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.pricingengine.marginprice.model.MarginPrice;

public record PriceCollectionItem(long orderBookId, MarginPrice marginPrice) {
}

