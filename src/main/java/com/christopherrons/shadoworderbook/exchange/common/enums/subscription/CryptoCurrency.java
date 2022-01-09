package com.christopherrons.shadoworderbook.exchange.common.enums.subscription;

public enum CryptoCurrency {
    INVALID_CASH_CURRENCY("Invalid Crypto Currency", ""),
    XRP("xrp", "Ripple - xrp"),
    BTC("btc", "Bitcoin");

    private final String name;
    private final String description;

    CryptoCurrency(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
