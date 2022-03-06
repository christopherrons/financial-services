package com.christopherrons.pricingengine.cache;

import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PricingCache {

    private final Map<TradingPairEnum, PriceSnapshot> tradingPairToPriceSnaphot = new ConcurrentHashMap<>();

    public PriceSnapshot findOrCreateSnapshot(final TradingPairEnum tradingPairEnum) {
        return tradingPairToPriceSnaphot.computeIfAbsent(tradingPairEnum, PriceSnapshot::new);
    }

    public List<PriceSnapshot> getAllPriceSnapshots() {
        return !tradingPairToPriceSnaphot.values().isEmpty() ? new ArrayList<>(tradingPairToPriceSnaphot.values()) :
                Collections.emptyList();
    }
}
