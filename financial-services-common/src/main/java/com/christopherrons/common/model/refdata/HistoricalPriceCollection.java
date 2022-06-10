package com.christopherrons.common.model.refdata;

import java.util.List;
import java.util.Map;

public record HistoricalPriceCollection(Map<String, HistoricalPrice> instrumentIdToHistoricalDataItem) {

    public List<Double> getHistoricalClosingPrices(String instrumentId) {
        return getHistoricalDataItem(instrumentId).getClosingPrices();
    }

    public HistoricalPrice getHistoricalDataItem(String instrumentId) {
        return instrumentIdToHistoricalDataItem.get(instrumentId);
    }
}
