package com.christopherrons.marketdata.common.enums.event;

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
        return switch (value.toLowerCase()) {
            case "order" -> ORDER;
            case "trade" -> TRADE;
            case "order_book" -> ORDER_BOOK;
            default -> INVALID_EVENT_TYPE;
        };
    }

    public String getValue() {
        return value;
    }
}