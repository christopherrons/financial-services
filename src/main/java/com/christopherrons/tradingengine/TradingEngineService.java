package com.christopherrons.tradingengine;

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

import java.util.Collection;
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
        for (MarketDataOrder order : event.getOrders()) {
            final Orderbook orderbook = orderbookService.updateAndGetOrderbook(order);
            OrderbookUpdate orderbookUpdate = orderbookIdToOrderbookUpdate.computeIfAbsent(order.getOrderbookId(),
                    key -> new OrderbookUpdate());

            MatchingEngineResult matchingEngineResult = orderbook.runMatchingEngine();
            if (!matchingEngineResult.isEmpty()) {
                orderbookUpdate.addMatchingResult(matchingEngineResult);
            }

            orderbookUpdate.setBestAskPrice(orderbook.getBestAskPrice());
            orderbookUpdate.setBestBidPrice(orderbook.getBestBidPrice());
        }

        broadCastEvents(orderbookIdToOrderbookUpdate.values());
    }

    private void broadCastEvents(final Collection<OrderbookUpdate> orderbookUpdates) {
        if (!orderbookUpdates.isEmpty()) {
            applicationEventPublisher.publishEvent(new OrderbookUpdateBroadcast(this, orderbookUpdates));
        }
    }

}
