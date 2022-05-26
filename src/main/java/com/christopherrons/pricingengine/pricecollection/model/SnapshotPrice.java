package com.christopherrons.pricingengine.pricecollection.model;

public class SnapshotPrice {

    private final String instrumentId;
    private double bidPrice;
    private double askPrice;
    private double lastPrice;

    public SnapshotPrice(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentId() {
        return instrumentId;
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
