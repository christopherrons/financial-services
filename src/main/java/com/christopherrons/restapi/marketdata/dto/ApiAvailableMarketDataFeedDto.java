package com.christopherrons.restapi.marketdata.dto;

import java.util.List;

public class ApiAvailableMarketDataFeedDto {

    private List<String> marketDataFeedNames;

    public ApiAvailableMarketDataFeedDto(List<String> marketDataFeedNames) {
        this.marketDataFeedNames = marketDataFeedNames;
    }

    public List<String> getMarketDataFeedNames() {
        return marketDataFeedNames;
    }

    public void setMarketDataFeedNames(List<String> marketDataFeedNames) {
        this.marketDataFeedNames = marketDataFeedNames;
    }
}
