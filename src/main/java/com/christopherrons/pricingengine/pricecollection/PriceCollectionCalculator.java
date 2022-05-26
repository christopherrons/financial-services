package com.christopherrons.pricingengine.pricecollection;

import com.christopherrons.pricingengine.marginprice.MarginPriceCalculator;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollectionItem;
import com.christopherrons.pricingengine.pricecollection.model.SnapshotPrice;
import com.christopherrons.pricingengine.yieldcurve.YieldCurve;
import com.christopherrons.refdata.historicalprices.model.HistoricalPriceCollection;
import com.christopherrons.refdata.historicalprices.model.HistoricalPrice;
import com.christopherrons.refdata.yield.model.YieldRefData;

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
