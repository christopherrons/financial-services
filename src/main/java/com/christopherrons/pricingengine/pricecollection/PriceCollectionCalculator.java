package com.christopherrons.pricingengine.pricecollection;

import com.christopherrons.pricingengine.marginprice.MarginPriceCalculator;
import com.christopherrons.pricingengine.marginprice.model.MarginPrice;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollectionItem;
import com.christopherrons.pricingengine.pricecollection.model.PriceSnapshot;
import com.christopherrons.pricingengine.yieldcurve.YieldCurve;
import com.christopherrons.refdata.yield.model.YieldRefData;

import java.util.List;

public class PriceCollectionCalculator {

    private static final MarginPriceCalculator marginPriceCalculator = new MarginPriceCalculator();

    public PriceCollection createPriceCollection(final List<PriceSnapshot> snapshots, final YieldRefData yieldRefData) {
        PriceCollection priceCollection = new PriceCollection();
        priceCollection.setYieldCurve(new YieldCurve(yieldRefData));
        addPriceCollectionItems(priceCollection.getPriceCollectionItems(), snapshots);
        return priceCollection;
    }

    private void addPriceCollectionItems(final List<PriceCollectionItem> priceCollectionItems, List<PriceSnapshot> snapshots) {
        for (PriceSnapshot snapshot : snapshots) {
            final PriceCollectionItem priceCollectionItem = new PriceCollectionItem(snapshot.getTradingPairEnum());
            priceCollectionItem.setMarginPrice(calculateMarginPrice(snapshot));

            priceCollectionItems.add(priceCollectionItem);
        }
    }

    public MarginPrice calculateMarginPrice(final PriceSnapshot snapshot) {
        return marginPriceCalculator.calculateMarginPrice(snapshot);

    }
}
