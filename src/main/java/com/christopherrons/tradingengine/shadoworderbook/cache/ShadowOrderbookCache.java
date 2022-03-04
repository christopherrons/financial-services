package com.christopherrons.tradingengine.shadoworderbook.cache;

import com.christopherrons.tradingengine.shadoworderbook.model.ShadowOrderbook;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShadowOrderbookCache {

    private final Map<String, ShadowOrderbook> orderbookIdToOrderBook = new ConcurrentHashMap<>();

    public ShadowOrderbook findOrCreate(final String orderbookId) {
        return orderbookIdToOrderBook.computeIfAbsent(orderbookId, key -> new ShadowOrderbook());
    }

}
