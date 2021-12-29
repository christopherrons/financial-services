package com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums;

import com.christopherrons.shadoworderbook.exchange.api.enums.EventDescriptionEnum;

public enum BitstampEventDescriptionEnum implements EventDescriptionEnum {
    NONE("none"),
    ORDER_CREATED("order_created"),
    ORDER_DELETED("order_deleted"),
    ORDER_UPDATED("order_changed"),
    TRADE("trade"),
    SUBSCRIPTION_SUCCEEDED("bts:subscription_succeeded"),
    FORCED_RECONNECT("bts:request_reconnect");

    private final String value;

    BitstampEventDescriptionEnum(String value) {
        this.value = value;
    }

    public static BitstampEventDescriptionEnum fromValue(String value) {
        switch (value.toLowerCase()) {
            case "order_created":
                return ORDER_CREATED;
            case "order_deleted":
                return ORDER_DELETED;
            case "order_changed":
                return ORDER_UPDATED;
            case "trade":
                return TRADE;
            case "bts:subscription_succeeded":
                return SUBSCRIPTION_SUCCEEDED;
            case "bts:request_reconnect":
                return FORCED_RECONNECT;
            default:
                return NONE;
        }
    }

    public String getValue() {
        return value;
    }
}
