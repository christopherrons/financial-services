package com.christopherrons.shadoworderbook.exchange.common.enums.subscription;

public enum CashCurrencyEnum {
    INVALID_CASH_CURRENCY("Invalid Cach Currency", ""),
    USD("xrp", "US Dollar"),
    EUR("eur", "Euro");

    private final String name;
    private final String description;

    CashCurrencyEnum(String name, String description) {
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
