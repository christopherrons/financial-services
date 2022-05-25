package com.christopherrons.pricingengine.cache;

import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PricingCache {

    private final Map<String, PriceSnapshot> instrumentIdToPriceSnapshot = new ConcurrentHashMap<>();

    public PriceSnapshot findOrCreateSnapshot(final String instrumentId) {
        return instrumentIdToPriceSnapshot.computeIfAbsent(instrumentId, PriceSnapshot::new);
    }

    public List<PriceSnapshot> getAllPriceSnapshots() {
        return !instrumentIdToPriceSnapshot.values().isEmpty() ? new ArrayList<>(instrumentIdToPriceSnapshot.values()) :
                Collections.emptyList();
    }
}
