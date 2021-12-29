package com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums;

import com.christopherrons.shadoworderbook.exchange.api.enums.ChannelEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.OrderOperationEnum;

public enum BitstampChannelEnum implements ChannelEnum {
    INVALID_CHANNEL("Invalid Channel"),
    LIVE_ORDERS("live_orders"),
    LIVE_TRADES("live_trades");

    private final String value;

    BitstampChannelEnum(String value) {
        this.value = value;
    }

    public static BitstampChannelEnum fromValue(String value) {
        switch (value.toLowerCase()) {
            case "live_orders":
                return LIVE_ORDERS;
            case "live_trades":
                return LIVE_TRADES;
            default:
                return INVALID_CHANNEL;
        }
    }

    public static BitstampChannelEnum extractValue(String value) {
        for (BitstampChannelEnum channelEnum: BitstampChannelEnum.values()) {
            if (value.contains(channelEnum.getValue())) {
                return channelEnum;
            }
        }
        return BitstampChannelEnum.INVALID_CHANNEL;
    }

    public String getValue() {
        return value;
    }
}
