package com.christopherrons.tradingengine;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.matchingengine.MatchingEngineService;
import com.christopherrons.tradingengine.shadoworderbook.model.ShadowOrderbook;
import com.christopherrons.tradingengine.shadoworderbook.service.ShadowOrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradingEngineService {

    @Autowired
    private ShadowOrderbookService shadowOrderbookService;
    @Autowired
    private MatchingEngineService matchingEngineService;

    public void runMatchingEngine(final MarketDataOrder order) {
        ShadowOrderbook orderbook = shadowOrderbookService.updateAndGetOrderbook(order);
        matchingEngineService.runMatchingEngine(orderbook);
    }
}
