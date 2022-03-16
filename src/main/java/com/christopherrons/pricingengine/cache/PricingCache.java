package com.christopherrons.pricingengine.cache;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PricingCache {

    private final Map<TradingPairEnum, PriceSnapshot> tradingPairToPriceSnapshot = new ConcurrentHashMap<>();

    public PriceSnapshot findOrCreateSnapshot(final TradingPairEnum tradingPairEnum) {
        return tradingPairToPriceSnapshot.computeIfAbsent(tradingPairEnum, PriceSnapshot::new);
    }

    public List<PriceSnapshot> getAllPriceSnapshots() {
        return !tradingPairToPriceSnapshot.values().isEmpty() ? new ArrayList<>(tradingPairToPriceSnapshot.values()) :
                Collections.emptyList();
    }
}
