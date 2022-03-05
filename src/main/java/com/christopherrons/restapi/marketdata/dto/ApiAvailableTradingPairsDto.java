package com.christopherrons.restapi.marketdata.dto;

import java.util.List;

public class ApiAvailableTradingPairsDto {

    private List<String> tradingPairs;

    public ApiAvailableTradingPairsDto(List<String> tradingPairs) {
        this.tradingPairs = tradingPairs;
    }

    public List<String> getTradingPairs() {
        return tradingPairs;
    }

    public void setTradingPairs(List<String> tradingPairs) {
        this.tradingPairs = tradingPairs;
    }
}
