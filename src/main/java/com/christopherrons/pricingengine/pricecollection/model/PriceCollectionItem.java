package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;

public class PriceCollectionItem {

    private final TradingPairEnum tradingPairEnum;
    private MarginPrice marginPrice;

    public PriceCollectionItem(TradingPairEnum tradingPairEnum) {
        this.tradingPairEnum = tradingPairEnum;
    }

    public void setMarginPrice(MarginPrice marginPrice) {
        this.marginPrice = marginPrice;
    }

    public MarginPrice getMarginPrice() {
        return marginPrice;
    }

    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }
}

