package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;

public class ApiAvailableChannelRequest {
    private MarketDataFeedEnum marketDataFeed;

    public MarketDataFeedEnum getMarketDataFeed() {
        return marketDataFeed;
    }

    public void setMarketDataFeed(MarketDataFeedEnum marketDataFeed) {
        this.marketDataFeed = marketDataFeed;
    }

    @Override
    public String toString() {
        return "AvailableTradingPairsRequest{" +
                "dataFeedName='" + marketDataFeed + '\'' +
                '}';
    }
}
