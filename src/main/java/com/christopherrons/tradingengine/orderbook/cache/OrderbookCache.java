package com.christopherrons.tradingengine.orderbook.cache;

import com.christopherrons.tradingengine.orderbook.orderbook.FifoOrderBook;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class OrderbookCache {

    private final Map<String, Orderbook> orderbookIdToOrderBook = new ConcurrentHashMap<>();

    public Orderbook findOrCreate(final String orderbookId) {
        return orderbookIdToOrderBook.computeIfAbsent(orderbookId, key -> new FifoOrderBook());
    }

}
