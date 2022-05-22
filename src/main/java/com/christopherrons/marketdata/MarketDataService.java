package com.christopherrons.marketdata;

import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.misc.comparators.EventComparator;
import com.christopherrons.common.misc.datastructures.TimeBoundPriorityQueue;
import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.EventLogging;
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
    private static final int TIME_IN_QUEUE_MS = 10000;
    private static final EventLogging eventLogging = new EventLogging();
    private final Map<Instrument, TimeBoundPriorityQueue<MarketDataEvent>> instrumentToEventPriorityQueue = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void handleEvent(final MarketDataEvent event) {
        TimeBoundPriorityQueue<MarketDataEvent> queue = findOrCreateQueue(event);
        handleEvents(queue.addItemThenPurge(event));
        eventLogging.logEvent();
    }

    private TimeBoundPriorityQueue<MarketDataEvent> findOrCreateQueue(final MarketDataEvent event) {
        return instrumentToEventPriorityQueue.computeIfAbsent(
                event.getInstrument(),
                e -> new TimeBoundPriorityQueue<>(TIME_IN_QUEUE_MS, new EventComparator<>())
        );
    }

    private void handleEvents(final List<MarketDataEvent> events) {
        List<MarketDataOrder> orders = new ArrayList<>();
        List<MarketDataTrade> trades = new ArrayList<>();
        for (MarketDataEvent event : events) {
            switch (event.getEventTypeEnum()) {
                case ORDER -> orders.add((MarketDataOrder) event); //TODO: might be wierd with cancell as we do our own matching
                case TRADE -> trades.add((MarketDataTrade) event);
            }
        }
        broadCastEvents(orders, trades);
    }

    private void broadCastEvents(final List<MarketDataOrder> orders, final List<MarketDataTrade> trades) {
        if (!orders.isEmpty()) {
            applicationEventPublisher.publishEvent(new OrderEventBroadcast(this, orders));
        }

        if (!trades.isEmpty()) {
            applicationEventPublisher.publishEvent(new TradeEventBroadcast(this, trades));
        }
    }
}
