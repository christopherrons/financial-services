package com.christopherrons.tradingengine;

import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.MatchingEngineService;
import com.christopherrons.tradingengine.shadoworderbook.ShadowOrderbookService;
import com.christopherrons.tradingengine.shadoworderbook.model.ShadowOrderbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TradingEngineService {

    @Autowired
    private ShadowOrderbookService shadowOrderbookService;
    @Autowired
    private MatchingEngineService matchingEngineService;


    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        for (MarketDataOrder order : event.getOrders()) {
            ShadowOrderbook orderbook = shadowOrderbookService.updateAndGetOrderbook(order);
            runMatchingEngine(orderbook);
        }
    }

    private void runMatchingEngine(final ShadowOrderbook orderbook) {
        matchingEngineService.runMatchingEngine(orderbook);
    }
}
