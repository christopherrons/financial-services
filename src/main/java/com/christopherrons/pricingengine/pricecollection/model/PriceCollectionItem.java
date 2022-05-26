package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.pricingengine.marginprice.model.MarginPrice;
import com.christopherrons.refdata.historicalprices.model.HistoricalPrice;

public record PriceCollectionItem(String instrumentId, MarginPrice marginPrice,
                                  HistoricalPrice historicalPrice) {
}

