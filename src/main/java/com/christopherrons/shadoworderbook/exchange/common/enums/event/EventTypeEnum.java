package com.christopherrons.shadoworderbook.exchange.common.enums.event;

public enum EventTypeEnum {
    INVALID_EVENT_TYPE("Invalid Event Type"),
    ORDER("order"),
    TRADE("trade"),
    ORDER_BOOK("orderBook");

    private final String value;

    EventTypeEnum(String value) {
        this.value = value;
    }

    public EventTypeEnum fromValue(String value) {
        switch (value.toLowerCase()) {
            case "order":
                return ORDER;
            case "TRADE":
                return TRADE;
            case "order_book":
                return ORDER_BOOK;
            default:
                return INVALID_EVENT_TYPE;
        }
    }

    public String getValue() {
        return value;
    }
}