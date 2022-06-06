package com.christopherrons.common.misc.comparators;

import com.christopherrons.common.api.marketdata.MarketDataOrder;

import java.util.Comparator;

public class FifoOrderBookComparator implements Comparator<MarketDataOrder> {
    @Override
    public int compare(MarketDataOrder order, MarketDataOrder otherEvent) {
        if (order.getTimeStampMs() < otherEvent.getTimeStampMs()) {
            return -1;
        } else {
            return order.getOrderId() == (otherEvent.getOrderId()) ? 0 : 1;
        }

    }

}
