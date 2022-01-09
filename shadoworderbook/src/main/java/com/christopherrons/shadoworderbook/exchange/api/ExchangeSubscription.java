package com.christopherrons.shadoworderbook.exchange.api;

public interface ExchangeSubscription {
    boolean isSubscribed();

    void unsubscribe();

    void subscribe();
}
