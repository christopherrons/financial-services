package com.christopherrons.tradingengine.matchingengine.model;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MatchingEngineResult {

    private final List<MarketDataTrade> trades = new ArrayList<>();
    private final Set<MarketDataOrder> orders = ConcurrentHashMap.newKeySet();


    public boolean isEmpty() {
        return false;
    }

    public void addTrade(final MarketDataTrade trade) {
        trades.add(trade);
    }

    public void addOrder(final MarketDataOrder order) {
        if (!orders.add(order)) {
            orders.remove(order);
            orders.add(order);
        }
    }

    public List<MarketDataTrade> getTrades() {
        return trades;
    }

    public Set<MarketDataOrder> getOrders() {
        return orders;
    }
}
