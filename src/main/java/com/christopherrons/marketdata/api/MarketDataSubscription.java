package com.christopherrons.marketdata.api;

public interface MarketDataSubscription {
    boolean isSubscribed();

    void unsubscribe();

    void subscribe();
}
