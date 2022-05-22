package com.christopherrons.tradingengine.orderbook.model;

import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;

import java.util.ArrayList;
import java.util.List;

public class OrderbookUpdate {

    private double bestBid = 0;
    private double bestAsk = 0;

    private long orderbookId;

    public OrderbookUpdate(long orderbookId) {
        this.orderbookId = orderbookId;
    }

    public void setBestBidPrice(double bestBid) {
        this.bestBid = bestBid;
    }

    public void setBestAskPrice(double bestAsk) {
        this.bestAsk = bestAsk;
    }

    public long getOrderbookId() {
        return orderbookId;
    }

    public double getBestBid() {
        return bestBid;
    }

    public double getBestAsk() {
        return bestAsk;
    }
}
