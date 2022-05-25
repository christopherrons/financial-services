package com.christopherrons.refdata.historicalprices.model;

import java.util.List;

public record HistoricalPriceItem(List<Double> closingPrices) {
    public List<Double> getClosingPrices() {
        return closingPrices;
    }
}
