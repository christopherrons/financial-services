package com.christopherrons.tradingengine.orderbook.model;

public class OrderbookUpdate {

    private double bestBid = 0;
    private double bestAsk = 0;

    private final String orderbookId;
    private final String instrumentId;

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
