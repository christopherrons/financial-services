package com.christopherrons.common.model.pricing;

import com.christopherrons.common.model.refdata.HistoricalPrice;

public record PriceCollectionItem(String instrumentId, MarginPrice marginPrice,
                                  HistoricalPrice historicalPrice) {
}

