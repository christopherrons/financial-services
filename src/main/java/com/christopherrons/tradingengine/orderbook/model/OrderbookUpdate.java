package com.christopherrons.tradingengine.orderbook.model;

import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;

import java.util.ArrayList;
import java.util.List;

public class OrderbookUpdate {

    private final List<MatchingEngineResult> matchingEngineResults = new ArrayList<>();
    private double bestBid = 0;
    private double bestAsk = 0;



    public void addMatchingResult(final MatchingEngineResult matchingEngineResult) {
        matchingEngineResults.add(matchingEngineResult);
    }

    public void setBestBidPrice(double bestBid) {
        this.bestBid = bestBid;
    }

    public void setBestAskPrice(double bestAsk) {
        this.bestAsk = bestAsk;
    }
}
