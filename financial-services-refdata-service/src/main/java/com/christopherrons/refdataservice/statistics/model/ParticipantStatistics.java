package com.christopherrons.refdataservice.statistics.model;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParticipantStatistics {

    private final Map<String, OrderbookStatistics> orderbookIdToOrderbookStatistics = new ConcurrentHashMap<>();
    private final String participantId;

    public ParticipantStatistics(String participantId) {
        this.participantId = participantId;
    }

    public void update(final MarketDataOrder order) {
        orderbookIdToOrderbookStatistics.computeIfAbsent(order.getOrderbookId(), OrderbookStatistics::new).update(order);
    }

    public void update(final MarketDataTrade trade) {
        orderbookIdToOrderbookStatistics.computeIfAbsent(trade.getOrderbookId(), OrderbookStatistics::new).update(trade);
    }

    public int getNumberOfOrderbooks() {
        return orderbookIdToOrderbookStatistics.size();
    }

    public String getParticipantId() {
        return participantId;
    }

}
