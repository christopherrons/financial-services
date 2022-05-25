package com.christopherrons.pricingengine.pricecollection;

import com.christopherrons.pricingengine.marginprice.MarginPriceCalculator;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollectionItem;
import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;
import com.christopherrons.pricingengine.yieldcurve.YieldCurve;
import com.christopherrons.refdata.historicalprices.model.HistoricalPrices;
import com.christopherrons.refdata.yield.model.YieldRefData;

import java.util.ArrayList;
import java.util.List;

public class PriceCollectionCalculator {

    private static final MarginPriceCalculator marginPriceCalculator = new MarginPriceCalculator();

    public PriceCollection createPriceCollection(final List<PriceSnapshot> snapshots,
                                                 final HistoricalPrices historicalPrices,
                                                 final YieldRefData yieldRefData) {
        return new PriceCollection(
                addPriceCollectionItems(snapshots, historicalPrices),
                new YieldCurve(yieldRefData)
        );
    }

    private List<PriceCollectionItem> addPriceCollectionItems(final List<PriceSnapshot> snapshots,
                                                              final HistoricalPrices historicalPrices) {
        final List<PriceCollectionItem> priceCollectionItems = new ArrayList<>();
        for (PriceSnapshot snapshot : snapshots) {
            priceCollectionItems.add(createPriceCollectionItem(snapshot,
                    historicalPrices.getHistoricalClosingPrices(snapshot.getInstrumentId())));
        }
        return priceCollectionItems;
    }

    private PriceCollectionItem createPriceCollectionItem(final PriceSnapshot snapshot, final List<Double> historicalClosingPrices) {
        return new PriceCollectionItem(
                snapshot.getInstrumentId(),
                calculateMarginPrice(snapshot),
                historicalClosingPrices
        );
    }

    public MarginPrice calculateMarginPrice(final PriceSnapshot snapshot) {
        return marginPriceCalculator.calculateMarginPrice(snapshot);
    }
}
