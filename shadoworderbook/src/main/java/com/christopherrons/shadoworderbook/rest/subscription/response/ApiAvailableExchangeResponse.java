package com.christopherrons.shadoworderbook.rest.subscription.response;

import java.util.List;

public class ApiAvailableExchangeResponse {

    private List<String> exchangeNames;

    public ApiAvailableExchangeResponse(List<String> exchangeNames) {
        this.exchangeNames = exchangeNames;
    }

    public List<String> getExchangeNames() {
        return exchangeNames;
    }

    public void setExchangeNames(List<String> exchangeNames) {
        this.exchangeNames = exchangeNames;
    }
}
