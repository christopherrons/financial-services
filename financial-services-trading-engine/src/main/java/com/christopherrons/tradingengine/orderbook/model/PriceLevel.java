package com.christopherrons.tradingengine.orderbook.model;

import com.christopherrons.common.api.marketdata.MarketDataOrder;

import java.util.Comparator;
import java.util.TreeSet;

public class PriceLevel extends TreeSet<MarketDataOrder> {

    private final double price;

    public PriceLevel(double price, Comparator<? super MarketDataOrder> comparator) {
        super(comparator);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public long nrOfOrdersAtPriceLevel() {
        return size();
    }

    public double volumeAtPriceLevel() {
        return stream().mapToDouble(MarketDataOrder::getCurrentVolume).sum();
    }

}
