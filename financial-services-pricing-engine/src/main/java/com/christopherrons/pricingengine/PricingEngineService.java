package com.christopherrons.pricingengine;

import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.broadcasts.OrderbookSnapshotBroadcast;
import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.broadcasts.TriggerPriceCollectionBroadcast;
import com.christopherrons.common.model.pricing.PriceCollection;
import com.christopherrons.common.model.trading.OrderbookSnapshot;
import com.christopherrons.pricingengine.cache.PricingCache;
import com.christopherrons.pricingengine.pricecollection.PriceCollectionCalculator;
import com.christopherrons.pricingengine.pricecollection.model.SnapshotPrice;
import com.christopherrons.refdataservice.RefDataService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.slf4j.Logger;


@Service
public class PricingEngineService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PricingEngineService.class);
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
    public void onMatching(OrderbookSnapshotBroadcast orderbookSnapshotBroadcast) {
        for (OrderbookSnapshot orderbookSnapShot : orderbookSnapshotBroadcast.getOrderbookSnapshots()) {
            SnapshotPrice snapshotPrice = pricingCache.findOrCreateSnapshot(orderbookSnapShot.getInstrumentId());
            snapshotPrice.setBidPrice(orderbookSnapShot.getBestBid());
            snapshotPrice.setAskPrice(orderbookSnapShot.getBestAsk());
        }
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast tradeEventBroadcast) {
        for (MarketDataTrade trade : tradeEventBroadcast.getTrades()) {
            pricingCache.findOrCreateSnapshot(trade.getInstrument().getInstrumentId())
                    .setLastPrice(trade.getPrice());
        }
    }

    @EventListener
    public void onTriggerPriceCollectionBroadCast(TriggerPriceCollectionBroadcast triggerPriceCollectionBroadcast) throws IOException {
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
