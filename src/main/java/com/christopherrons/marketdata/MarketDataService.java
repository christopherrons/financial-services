package com.christopherrons.marketdata;

import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.misc.comparators.EventComparator;
import com.christopherrons.common.misc.datastructures.TimeBoundPriorityQueue;
import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.instrument.api.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class MarketDataService {
    private static final Logger LOGGER = Logger.getLogger(MarketDataService.class.getName());
    private final Map<Instrument, TimeBoundPriorityQueue<MarketDataEvent>> instrumentToEventPriorityQueue = new ConcurrentHashMap<>();
    private static final int TIME_IN_MS = 10000;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void handleEvent(final MarketDataEvent event) {
        TimeBoundPriorityQueue<MarketDataEvent> queue = findOrCreateQueue(event);
        handleEvent(queue.addItemThenPurge(event));
    }

    private TimeBoundPriorityQueue<MarketDataEvent> findOrCreateQueue(final MarketDataEvent event) {
        return instrumentToEventPriorityQueue.computeIfAbsent(
                event.getInstrument(),
                e -> new TimeBoundPriorityQueue<>(TIME_IN_MS, new EventComparator<>())
        );
    }

    private void handleEvent(final List<MarketDataEvent> events) {
        List<MarketDataOrder> orders = new ArrayList<>();
        List<MarketDataTrade> trades = new ArrayList<>();
        for (MarketDataEvent event : events) {
            switch (event.getEventTypeEnum()) {
                case ORDER -> orders.add((MarketDataOrder) event);
                case TRADE -> trades.add((MarketDataTrade) event);
            }
        }
        broadCastEvents(orders, trades);
    }

    private void broadCastEvents(final List<MarketDataOrder> orders, final List<MarketDataTrade> trades) {
        if (!orders.isEmpty()) {
            broadcastOrders(orders);
        }

        if (!trades.isEmpty()) {
            broadcastTrades(trades);
        }
    }

    private void broadcastOrders(final List<MarketDataOrder> orders) {
       // LOGGER.info(String.format("Broadcasting %s orders,", orders.size()));
        applicationEventPublisher.publishEvent(new OrderEventBroadcast(this, orders));
    }

    private void broadcastTrades(final List<MarketDataTrade> trades) {
       // LOGGER.info(String.format("Broadcasting %s trades.", trades.size()));
        applicationEventPublisher.publishEvent(new TradeEventBroadcast(this, trades));
    }
}
