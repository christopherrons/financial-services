package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;

public class ApiAvailableTradingPairsRequest {
    private MarketDataFeedEnum marketDataFeed;

    public MarketDataFeedEnum getDataFeedName() {
        return marketDataFeed;
    }

    public void setMarketDataFeed(MarketDataFeedEnum dataFeedName) {
        this.marketDataFeed = dataFeedName;
    }

    @Override
    public String toString() {
        return "AvailableTradingPairsRequest{" +
                "marketDataFeedEnum='" + marketDataFeed + '\'' +
                '}';
    }
}
