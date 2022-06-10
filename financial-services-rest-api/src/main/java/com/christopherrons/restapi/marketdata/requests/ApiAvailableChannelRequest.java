package com.christopherrons.restapi.marketdata.requests;

import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;

public class ApiAvailableChannelRequest {
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
