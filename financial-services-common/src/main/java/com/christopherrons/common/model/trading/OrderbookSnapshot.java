package com.christopherrons.common.model.trading;

import java.util.ArrayList;
import java.util.List;

public class OrderbookSnapshot {

    private final String orderbookId;
    private final String instrumentId;
    private double bestBid = 0;
    private double bestAsk = 0;

    private List<PriceLevelData> askPriceLevelData = new ArrayList<>();
    private List<PriceLevelData> bidPriceLevelData = new ArrayList<>();


    public OrderbookSnapshot(String orderbookId, String instrumentId) {
        this.orderbookId = orderbookId;
        this.instrumentId = instrumentId;
    }

    public void setBestBidPrice(final double bestBid) {
        this.bestBid = bestBid;
    }

    public void setBestAskPrice(final double bestAsk) {
        this.bestAsk = bestAsk;
    }

    public void addPriceLevelData(final int priceLevel, final double price, final double volume, final boolean isBid) {
        if (isBid) {
            bidPriceLevelData.add(new PriceLevelData(priceLevel, price, volume));
        } else {
            askPriceLevelData.add(new PriceLevelData(priceLevel, price, volume));
        }
    }

    public List<PriceLevelData> getAskPriceLevelData() {
        return askPriceLevelData;
    }

    public List<PriceLevelData> getBidPriceLevelData() {
        return bidPriceLevelData;
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
