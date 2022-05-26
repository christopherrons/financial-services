package com.christopherrons.pricingengine;

import com.christopherrons.common.broadcasts.MatchingEngineBroadcast;
import com.christopherrons.common.broadcasts.OrderbookUpdateBroadcast;
import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.pricingengine.cache.PricingCache;
import com.christopherrons.pricingengine.pricecollection.PriceCollectionCalculator;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.SnapshotPrice;
import com.christopherrons.refdata.RefDataService;
import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import com.christopherrons.tradingengine.orderbook.model.OrderbookUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;


@Service
public class PricingEngineService {

    private static final Logger LOGGER = Logger.getLogger(PricingEngineService.class.getName());
    private static final PriceCollectionCalculator priceCollectionCalculator = new PriceCollectionCalculator();
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RefDataService refDataService;
    private final PricingCache pricingCache = new PricingCache();

    @Autowired
    public PricingEngineService(ApplicationEventPublisher applicationEventPublisher, RefDataService refDataService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.refDataService = refDataService;
    }

    @EventListener
    public void onMatching(OrderbookUpdateBroadcast orderbookUpdateBroadcast) {
        for (OrderbookUpdate orderbookUpdate : orderbookUpdateBroadcast.getOrderbookUpdates()) {
            SnapshotPrice snapshotPrice = pricingCache.findOrCreateSnapshot(orderbookUpdate.getInstrumentId());
            snapshotPrice.setBidPrice(orderbookUpdate.getBestBid());
            snapshotPrice.setAskPrice(orderbookUpdate.getBestAsk());
        }
    }

    @EventListener
    public void onMatchingEngineEvent(MatchingEngineBroadcast matchingEngineBroadcast) {
        for (MatchingEngineResult result : matchingEngineBroadcast.getMatchingEngineResult()) {
            for (MarketDataTrade trade : result.getTrades()) {
                pricingCache.findOrCreateSnapshot(trade.getInstrument().getInstrumentId())
                        .setLastPrice(trade.getPrice());
            }
        }
    }

    public void createPriceCollection() throws IOException {
        LOGGER.info("Create Price Collections");

        PriceCollection priceCollectionItems = priceCollectionCalculator.createPriceCollection(
                pricingCache.getAllPriceSnapshots(),
                refDataService.getHistoricalData(),
                refDataService.getYieldRefData());
        broadcastPriceCollection(priceCollectionItems);
    }

    private void broadcastPriceCollection(final PriceCollection priceCollection) {
        LOGGER.info("Broadcasting price collection");
        applicationEventPublisher.publishEvent(new PriceCollectionsEventBroadcast(this, priceCollection));
    }
}
