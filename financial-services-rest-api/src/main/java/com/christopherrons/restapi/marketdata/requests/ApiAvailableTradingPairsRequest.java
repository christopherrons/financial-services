package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;

public class ApiAvailableTradingPairsRequest {
    private MarketDataFeedEnum marketDataFeedEnum;

    public MarketDataFeedEnum getDataFeedName() {
        return marketDataFeedEnum;
    }

    public void setMarketDataFeedEnum(MarketDataFeedEnum dataFeedName) {
        this.marketDataFeedEnum = dataFeedName;
    }

    @Override
    public String toString() {
        return "AvailableTradingPairsRequest{" +
                "marketDataFeedEnum='" + marketDataFeedEnum + '\'' +
                '}';
    }
}
