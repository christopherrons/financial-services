package com.christopherrons.pricingengine.marginprice.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;

public record MarginPrice(TradingPairEnum tradingPairEnum,
                          double marginPrice,
                          MarginPriceMethodEnum marginPriceMethodEnum) {
}
