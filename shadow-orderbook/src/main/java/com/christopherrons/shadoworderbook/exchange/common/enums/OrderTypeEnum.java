package com.christopherrons.shadoworderbook.exchange.common.enums;


public enum OrderTypeEnum {
    INVALID_ORDER_TYPE("Invalid Order Type"),
    BUY("buy"),
    SELL("sell");

    private final String value;

    OrderTypeEnum(String value) {
        this.value = value;
    }

    public OrderTypeEnum fromValue(String value) {
        switch (value.toLowerCase()) {
            case "buy":
                return BUY;
            case "sell":
                return SELL;
            default:
                return INVALID_ORDER_TYPE;
        }
    }

    public String getValue() {
        return value;
    }
}
