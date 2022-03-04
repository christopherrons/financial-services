package com.christopherrons.marketdata.api;

public interface MarketDataTrade extends MarketDataEvent {

    long getTradeId();

    long getSellOrderId();

    long getBuyOrderId();

    double getPrice();

    double getVolume();

    int getSideAggressor();
}
