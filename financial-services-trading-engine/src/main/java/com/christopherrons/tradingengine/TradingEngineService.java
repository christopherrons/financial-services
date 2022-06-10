package com.christopherrons.tradingengine;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.OrderbookUpdateBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.model.trading.MatchingEngineResult;
import com.christopherrons.common.model.trading.OrderbookUpdate;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
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
            OrderbookUpdate orderbookUpdate = orderbookIdToOrderbookUpdate.computeIfAbsent(orderbook.getOrderbookId(),
                    key -> new OrderbookUpdate(orderbook.getOrderbookId(), order.getInstrument().getInstrumentId()));

            MatchingEngineResult matchingEngineResult = orderbook.runMatchingEngine();
            if (!matchingEngineResult.isEmpty()) {
                matchingEngineResults.add(matchingEngineResult);
            }

            orderbookUpdate.setBestAskPrice(orderbook.getBestAskPrice());
            orderbookUpdate.setBestBidPrice(orderbook.getBestBidPrice());
        }

        broadCastTrades(matchingEngineResults.stream().flatMap(matchingEngineResult -> matchingEngineResult.getTrades().stream()).toList());
        broadCastOrderbookUpdates(orderbookIdToOrderbookUpdate.values());
    }

    private void broadCastTrades(final List<MarketDataTrade> trades) {
        if (!trades.isEmpty()) {
            applicationEventPublisher.publishEvent(new TradeEventBroadcast(this, trades));
        }
    }

    private void broadCastOrderbookUpdates(final Collection<OrderbookUpdate> orderbookUpdates) {
        if (!orderbookUpdates.isEmpty()) {
            applicationEventPublisher.publishEvent(new OrderbookUpdateBroadcast(this, orderbookUpdates));
        }
    }

}
