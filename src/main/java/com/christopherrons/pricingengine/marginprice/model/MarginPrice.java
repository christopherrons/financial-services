package com.christopherrons.pricingengine.marginprice.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.enums.MarginPriceMethodEnum;

public class MarginPrice {

    private final TradingPairEnum tradingPairEnum;
    private final double marginPrice;
    private final MarginPriceMethodEnum marginPriceMethodEnum;

    public MarginPrice(TradingPairEnum tradingPairEnum, double marginPrice, MarginPriceMethodEnum marginPriceMethodEnum) {
        this.tradingPairEnum = tradingPairEnum;
        this.marginPrice = marginPrice;
        this.marginPriceMethodEnum = marginPriceMethodEnum;
    }

    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }

    public double getMarginPrice() {
        return marginPrice;
    }

    public MarginPriceMethodEnum getMarginPriceMethodEnum() {
        return marginPriceMethodEnum;
    }
}
