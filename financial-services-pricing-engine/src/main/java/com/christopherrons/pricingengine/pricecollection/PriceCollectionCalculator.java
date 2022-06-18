package com.christopherrons.pricingengine.pricecollection;

import com.christopherrons.common.model.pricing.MarginPrice;
import com.christopherrons.common.model.pricing.PriceCollection;
import com.christopherrons.common.model.pricing.PriceCollectionItem;
import com.christopherrons.common.model.pricing.YieldCurve;
import com.christopherrons.common.model.refdata.HistoricalPrice;
import com.christopherrons.common.model.refdata.HistoricalPriceCollection;
import com.christopherrons.common.model.refdata.YieldRefData;
import com.christopherrons.pricingengine.marginprice.MarginPriceCalculator;
import com.christopherrons.pricingengine.pricecollection.model.SnapshotPrice;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PriceCollectionCalculator {

    private static final MarginPriceCalculator marginPriceCalculator = new MarginPriceCalculator();

    public PriceCollection createPriceCollection(final List<SnapshotPrice> snapshots,
                                                 final HistoricalPriceCollection historicalPriceCollection,
                                                 final YieldRefData yieldRefData) {
        return new PriceCollection(
                addPriceCollectionItems(snapshots, historicalPriceCollection),
                new YieldCurve(yieldRefData)
        );
    }

    private Map<String, PriceCollectionItem> addPriceCollectionItems(final List<SnapshotPrice> snapshots,
                                                                     final HistoricalPriceCollection historicalPriceCollection) {
        final Map<String, PriceCollectionItem> instrumentIdToPriceCollectionItems = new ConcurrentHashMap<>();
        for (SnapshotPrice snapshot : snapshots) {
            instrumentIdToPriceCollectionItems.put(snapshot.getInstrumentId(), createPriceCollectionItem(snapshot,
                    historicalPriceCollection.getHistoricalDataItem(snapshot.getInstrumentId())));
        }
        return instrumentIdToPriceCollectionItems;
    }

    private PriceCollectionItem createPriceCollectionItem(final SnapshotPrice snapshot, final HistoricalPrice historicalPrice) {
        return new PriceCollectionItem(
                snapshot.getInstrumentId(),
                calculateMarginPrice(snapshot),
                historicalPrice
        );
    }

    public MarginPrice calculateMarginPrice(final SnapshotPrice snapshot) {
        return marginPriceCalculator.calculateMarginPrice(snapshot);
    }
}
