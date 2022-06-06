package com.christopherrons.pricingengine.cache;

import com.christopherrons.pricingengine.pricecollection.model.SnapshotPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PricingCache {

    private final Map<String, SnapshotPrice> instrumentIdToPriceSnapshot = new ConcurrentHashMap<>();

    public SnapshotPrice findOrCreateSnapshot(final String instrumentId) {
        return instrumentIdToPriceSnapshot.computeIfAbsent(instrumentId, SnapshotPrice::new);
    }

    public List<SnapshotPrice> getAllPriceSnapshots() {
        return !instrumentIdToPriceSnapshot.values().isEmpty() ? new ArrayList<>(instrumentIdToPriceSnapshot.values()) :
                Collections.emptyList();
    }
}
