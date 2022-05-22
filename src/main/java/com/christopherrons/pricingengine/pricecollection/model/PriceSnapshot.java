package com.christopherrons.pricingengine.pricecollection.model;

public class PriceSnapshot {

    private final long orderbookId;
    private double bidPrice;
    private double askPrice;
    private double lastPrice;

    public PriceSnapshot(long orderbookId) {
        this.orderbookId = orderbookId;
    }

    public long getOrderbookId() {
        return orderbookId;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }
}
