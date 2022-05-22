package com.christopherrons.tradingengine;

import com.christopherrons.common.broadcasts.MatchingEngineBroadcast;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.OrderbookUpdateBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
import com.christopherrons.tradingengine.orderbook.model.OrderbookUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TradingEngineService {

    @Autowired
    private OrderbookService orderbookService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        Map<String, OrderbookUpdate> orderbookIdToOrderbookUpdate = new ConcurrentHashMap<>();
        List<MatchingEngineResult> matchingEngineResults = new ArrayList<>();
        for (MarketDataOrder order : event.getOrders()) {
            final Orderbook orderbook = orderbookService.updateAndGetOrderbook(order);
            OrderbookUpdate orderbookUpdate = orderbookIdToOrderbookUpdate.computeIfAbsent(order.getOrderbookId(),
                    key -> new OrderbookUpdate());

            MatchingEngineResult matchingEngineResult = orderbook.runMatchingEngine();
            if (!matchingEngineResult.isEmpty()) {
                matchingEngineResults.add(matchingEngineResult);
            }

            orderbookUpdate.setBestAskPrice(orderbook.getBestAskPrice());
            orderbookUpdate.setBestBidPrice(orderbook.getBestBidPrice());
        }

        broadCastMatchingResults(matchingEngineResults);
        broadCastOrderbookUpdates(orderbookIdToOrderbookUpdate.values());
    }

    private void broadCastMatchingResults(final Collection<MatchingEngineResult> matchingEngineResults) {
        if (!matchingEngineResults.isEmpty()) {
            applicationEventPublisher.publishEvent(new MatchingEngineBroadcast(this, matchingEngineResults));
        }
    }

    private void broadCastOrderbookUpdates(final Collection<OrderbookUpdate> orderbookUpdates) {
        if (!orderbookUpdates.isEmpty()) {
            applicationEventPublisher.publishEvent(new OrderbookUpdateBroadcast(this, orderbookUpdates));
        }
    }

}
