package com.christopherrons.marketdataservice.api;

public interface MarketDataSubscription {
    boolean isSubscribed();

    void unsubscribe();

    void subscribe();
}
