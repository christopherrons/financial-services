package com.christopherrons.tradingengine;

import com.christopherrons.common.broadcasts.OrderbookUpdateBroadcast;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradingEngineService {

    @Autowired
    private OrderbookService orderbookService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        List<MatchingEngineResult> matchingEngineResults = new ArrayList<>();
        for (MarketDataOrder order : event.getOrders()) {
            final Orderbook orderbook = orderbookService.updateAndGetOrderbook(order);
            MatchingEngineResult matchingEngineResult = orderbook.runMatchingEngine();

            if (!matchingEngineResult.isEmpty()) {
                matchingEngineResults.add(matchingEngineResult);
            }
        }
        broadCastEvents(matchingEngineResults);
    }

    private void broadCastEvents(final List<MatchingEngineResult> matchingEngineResults) {
        if (!matchingEngineResults.isEmpty()) {
            applicationEventPublisher.publishEvent(new OrderbookUpdateBroadcast(this, matchingEngineResults));
        }
    }

}
