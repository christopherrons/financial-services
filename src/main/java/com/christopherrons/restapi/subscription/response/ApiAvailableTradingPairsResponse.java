package com.christopherrons.restapi.subscription.response;

import java.util.List;

public class ApiAvailableTradingPairsResponse {

    private List<String> tradingPairs;

    public ApiAvailableTradingPairsResponse(List<String> tradingPairs) {
        this.tradingPairs = tradingPairs;
    }

    public List<String> getTradingPairs() {
        return tradingPairs;
    }

    public void setTradingPairs(List<String> tradingPairs) {
        this.tradingPairs = tradingPairs;
    }
}
