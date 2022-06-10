package com.christopherrons.refdataengine.statistics.cache;

import com.christopherrons.refdataengine.statistics.model.OrderbookStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderbookStatisticsCache {

    private final Map<String, OrderbookStatistics> orderbookIdToOrderbookStatistics = new ConcurrentHashMap<>();

    public OrderbookStatistics findOrCreateOrderbookStatistics(final String orderbookId) {
        return orderbookIdToOrderbookStatistics.computeIfAbsent(orderbookId, OrderbookStatistics::new);
    }

    public List<OrderbookStatistics> getOrderBookStatistics() {
        return orderbookIdToOrderbookStatistics.values().isEmpty() ?
                Collections.emptyList() :
                new ArrayList<>(orderbookIdToOrderbookStatistics.values());
    }
}
