package com.christopherrons.marketdata;

import com.christopherrons.common.model.EventComparator;
import com.christopherrons.common.model.TimeBoundPriorityQueue;
import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.refdata.RefDataService;
import com.christopherrons.tradingengine.TradingEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class MarketDataService {
    private static final Logger LOGGER = Logger.getLogger(MarketDataService.class.getName());
    private final Map<String, TimeBoundPriorityQueue<MarketDataEvent>> orderbookIdToEventPriorityQueue = new ConcurrentHashMap<>();
    private static final int TIME_IN_MS = 10000;

    @Autowired
    private TradingEngineService tradingEngineService;
    @Autowired
    private RefDataService refDataService;

    public void handleEvent(final MarketDataEvent event) {
  //      LOGGER.info(event.toString());

        TimeBoundPriorityQueue<MarketDataEvent> queue = findOrCreateQueue(event);
        handleEvent(queue.addItemThenPurge(event));
    }

    private TimeBoundPriorityQueue<MarketDataEvent> findOrCreateQueue(final MarketDataEvent event) {
        return orderbookIdToEventPriorityQueue.computeIfAbsent(
                event.getTradingPairEnum().getName(),
                e -> new TimeBoundPriorityQueue<>(TIME_IN_MS, new EventComparator<>())
        );
    }

    private void handleEvent(final List<MarketDataEvent> events) {
        for (MarketDataEvent event : events) {
            refDataService.addRefData(event);
            switch (event.getEventTypeEnum()) {
                case ORDER:
                    tradingEngineService.runMatchingEngine((MarketDataOrder) event);
                    break;
                case TRADE:
                    break;
            }
        }
    }
}
