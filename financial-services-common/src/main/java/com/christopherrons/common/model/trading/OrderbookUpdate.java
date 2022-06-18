package com.christopherrons.common.model.trading;

public class OrderbookUpdate {

    private final String orderbookId;
    private final String instrumentId;
    private double bestBid = 0;
    private double bestAsk = 0;

    public OrderbookUpdate(String orderbookId, String instrumentId) {
        this.orderbookId = orderbookId;
        this.instrumentId = instrumentId;
    }

    public void setBestBidPrice(double bestBid) {
        this.bestBid = bestBid;
    }

    public void setBestAskPrice(double bestAsk) {
        this.bestAsk = bestAsk;
    }

    public String getOrderbookId() {
        return orderbookId;
    }

    public double getBestBid() {
        return bestBid;
    }

    public double getBestAsk() {
        return bestAsk;
    }

    public String getInstrumentId() {
        return instrumentId;
    }
}
