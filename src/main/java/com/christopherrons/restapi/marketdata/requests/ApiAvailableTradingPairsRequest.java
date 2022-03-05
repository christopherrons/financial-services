package com.christopherrons.restapi.marketdata.requests;

public class ApiAvailableTradingPairsRequest {
    private String dataFeedName;

    public String getDataFeedName() {
        return dataFeedName;
    }

    public void setDataFeedName(String dataFeedName) {
        this.dataFeedName = dataFeedName;
    }

    @Override
    public String toString() {
        return "AvailableTradingPairsRequest{" +
                "dataFeedName='" + dataFeedName + '\'' +
                '}';
    }
}
