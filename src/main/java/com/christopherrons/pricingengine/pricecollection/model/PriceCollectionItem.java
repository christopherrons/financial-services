package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;

public record PriceCollectionItem(TradingPairEnum tradingPairEnum, MarginPrice marginPrice) {
}

