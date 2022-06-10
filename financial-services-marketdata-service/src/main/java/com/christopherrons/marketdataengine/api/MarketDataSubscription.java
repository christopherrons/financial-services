package com.christopherrons.marketdataengine.api;

public interface MarketDataSubscription {
    boolean isSubscribed();

    void unsubscribe();

    void subscribe();
}
