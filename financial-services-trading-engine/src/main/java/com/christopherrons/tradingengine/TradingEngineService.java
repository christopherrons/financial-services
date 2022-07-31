package com.christopherrons.tradingengine;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.OrderbookSnapshotBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.model.trading.MatchingEngineResult;
import com.christopherrons.common.model.trading.OrderbookSnapshot;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
        broadCastTrades(matchingEngineResults.stream().flatMap(matchingEngineResult -> matchingEngineResult.getTrades().stream()).toList());
        broadCastOrderbookSnapshot();
    }

    private void broadCastTrades(final List<MarketDataTrade> trades) {
        if (!trades.isEmpty()) {
            applicationEventPublisher.publishEvent(new TradeEventBroadcast(this, trades));
        }
    }

    private void broadCastOrderbookSnapshot() {
        List<OrderbookSnapshot> orderbookSnapshots = new ArrayList<>();
        for (Orderbook orderbook : orderbookService.getAllOrderbooks()) {
            var orderbookSnapshot = new OrderbookSnapshot(orderbook.getOrderbookId(), orderbook.getInstrumentId());
            orderbookSnapshot.setBestAskPrice(orderbook.getBestAskPrice());
            orderbookSnapshot.setBestBidPrice(orderbook.getBestBidPrice());
            for (int priceLevel = 1; priceLevel < 6; priceLevel++) {
                orderbookSnapshot.addPriceLevelData(priceLevel, orderbook.getBidPriceAtPriceLevel(priceLevel), orderbook.totalBidVolumeAtPriceLevel(priceLevel), true);
                orderbookSnapshot.addPriceLevelData(priceLevel, orderbook.getAskPriceAtPriceLevel(priceLevel), orderbook.totalAskVolumeAtPriceLevel(priceLevel), false);
            }
            orderbookSnapshots.add(orderbookSnapshot);
        }
        broadCastOrderbookSnapshot(orderbookSnapshots);
    }

    private void broadCastOrderbookSnapshot(final Collection<OrderbookSnapshot> orderbookSnapshots) {
        if (!orderbookSnapshots.isEmpty()) {
            applicationEventPublisher.publishEvent(new OrderbookSnapshotBroadcast(this, orderbookSnapshots));
        }
    }

}
