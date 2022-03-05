package com.christopherrons.tradingengine;

import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.MatchingEngineService;
import com.christopherrons.tradingengine.shadoworderbook.model.ShadowOrderbook;
import com.christopherrons.tradingengine.shadoworderbook.service.ShadowOrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class TradingEngineService implements ApplicationListener<OrderEventBroadcast> {

    @Autowired
    private ShadowOrderbookService shadowOrderbookService;
    @Autowired
    private MatchingEngineService matchingEngineService;


    @Override
    public void onApplicationEvent(OrderEventBroadcast event) {
        for (MarketDataOrder order : event.getOrders()) {
            ShadowOrderbook orderbook = shadowOrderbookService.updateAndGetOrderbook(order);
            runMatchingEngine(orderbook);
        }
    }

    private void runMatchingEngine(final ShadowOrderbook orderbook) {
        matchingEngineService.runMatchingEngine(orderbook);
    }
}
