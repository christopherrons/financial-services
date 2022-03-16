package com.christopherrons.pricingengine;

import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;
import com.christopherrons.pricingengine.cache.PricingCache;
import com.christopherrons.pricingengine.pricecollection.PriceCollectionCalculator;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;
import com.christopherrons.refdata.RefDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;


@Service
public class PricingService {

    private static final Logger LOGGER = Logger.getLogger(PricingService.class.getName());
    private static final PriceCollectionCalculator priceCollectionCalculator = new PriceCollectionCalculator();
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RefDataService refDataService;
    private final PricingCache pricingCache = new PricingCache();

    @Autowired
    public PricingService(ApplicationEventPublisher applicationEventPublisher, RefDataService refDataService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.refDataService = refDataService;
    }

    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        for (MarketDataOrder order : event.getOrders()) { //TODO: Not quite correct as the bid ask is alwyas the lastest order not the best
            PriceSnapshot priceSnapshot = pricingCache.findOrCreateSnapshot(order.getTradingPairEnum());
            switch (OrderTypeEnum.fromValue(order.getOrderType())) {
                case BUY -> priceSnapshot.setBidPrice(order.getPrice());
                case SELL -> priceSnapshot.setAskPrice(order.getPrice());
            }
        }
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast event) {
        for (MarketDataTrade trade : event.getTrades()) {
            pricingCache.findOrCreateSnapshot(trade.getTradingPairEnum())
                    .setLastPrice(trade.getPrice());
        }
    }

    public void createPriceCollection() throws IOException {
        LOGGER.info("Create Price Collections");

        PriceCollection priceCollectionItems = priceCollectionCalculator.createPriceCollection(
                pricingCache.getAllPriceSnapshots(),
                refDataService.getYieldRefData());
        broadcastPriceCollection(priceCollectionItems);
    }

    private void broadcastPriceCollection(final PriceCollection priceCollection) {
        LOGGER.info("Broadcasting price collection");
        applicationEventPublisher.publishEvent(new PriceCollectionsEventBroadcast(this, priceCollection));
    }
}
