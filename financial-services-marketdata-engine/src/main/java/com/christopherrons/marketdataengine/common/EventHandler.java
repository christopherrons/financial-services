package com.christopherrons.marketdataengine.common;

import com.christopherrons.common.api.marketdata.MarketDataEvent;
import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.enums.marketdata.OrderOperationEnum;
import com.christopherrons.common.misc.comparators.EventComparator;
import com.christopherrons.common.misc.datastructures.TimeBoundPriorityQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventHandler {
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
                case ORDER -> {
                    MarketDataOrder order = ((MarketDataOrder) event);
                    if (order.getOrderOperationEnum() != OrderOperationEnum.DELETE) {
                        // We skip cancel events as we built our own trading engine
                        orders.add((MarketDataOrder) event);
                    }
                }
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
