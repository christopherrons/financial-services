package com.christopherrons.common.misc.comparators;

import com.christopherrons.common.api.marketdata.MarketDataOrder;

import java.util.Comparator;

public class MarketOrderComparator implements Comparator<MarketDataOrder> {
    @Override
    public int compare(MarketDataOrder order, MarketDataOrder otherEvent) {
        return order.getOrderId() == (otherEvent.getOrderId()) ? 0 : 1;
    }
}

