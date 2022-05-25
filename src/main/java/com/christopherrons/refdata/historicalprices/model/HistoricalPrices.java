package com.christopherrons.refdata.historicalprices.model;

import java.util.List;
import java.util.Map;

public record HistoricalPrices(Map<String, HistoricalPriceItem> instrumentIdToHistoricalDataItem) {

    public List<Double> getHistoricalClosingPrices(String instrumentId) {
        return getHistoricalDataItem(instrumentId).getClosingPrices();
    }

    public HistoricalPriceItem getHistoricalDataItem(String instrumentId) {
        return instrumentIdToHistoricalDataItem.get(instrumentId);
    }
}
