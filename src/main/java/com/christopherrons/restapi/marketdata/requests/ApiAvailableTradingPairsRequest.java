package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;

public class ApiAvailableTradingPairsRequest {
    private MarketDataFeedEnum dataFeedName;

    public MarketDataFeedEnum getDataFeedName() {
        return dataFeedName;
    }

    public void setDataFeedName(MarketDataFeedEnum dataFeedName) {
        this.dataFeedName = dataFeedName;
    }

    @Override
    public String toString() {
        return "AvailableTradingPairsRequest{" +
                "dataFeedName='" + dataFeedName + '\'' +
                '}';
    }
}
