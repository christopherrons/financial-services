package com.christopherrons.restapi.subscription.request;

public class ApiAvailableTradingPairsRequest {
    private String exchangeName;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    @Override
    public String toString() {
        return "AvailableTradingPairsRequest{" +
                "exchangeName='" + exchangeName + '\'' +
                '}';
    }
}
