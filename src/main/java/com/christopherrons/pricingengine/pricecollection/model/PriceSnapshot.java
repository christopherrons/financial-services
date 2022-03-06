package com.christopherrons.pricingengine.pricecollection.model;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;

public class PriceSnapshot {

    private final TradingPairEnum tradingPairEnum;
    private double bidPrice;
    private double askPrice;
    private double lastPrice;

    public PriceSnapshot(TradingPairEnum tradingPairEnum) {
        this.tradingPairEnum = tradingPairEnum;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public TradingPairEnum getTradingPairEnum() {
        return tradingPairEnum;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }
}
