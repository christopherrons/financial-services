package com.christopherrons.marketdata.common.enums.event;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

public enum EventTypeEnum {
    INVALID_EVENT_TYPE("Invalid Event Type"),
    ORDER("order"),
    TRADE("trade"),
    ORDER_BOOK("orderBook");
    private static final Map<String, EventTypeEnum> VALUES_BY_IDENTIFIER =
            stream(EventTypeEnum.values()).collect(toMap(EventTypeEnum::getValue, identity()));
    private final String value;

    EventTypeEnum(String value) {
        this.value = value;
    }

    public EventTypeEnum fromValue(String value) {
        return VALUES_BY_IDENTIFIER.getOrDefault(value.toLowerCase(), INVALID_EVENT_TYPE);
    }

    public String getValue() {
        return value;
    }
}