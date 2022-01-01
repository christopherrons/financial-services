package com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums;

public enum BitstampTradingPairEnum {
    INVALID_TRADING_PAIR("Invalid Trading Pair"),
    XRP_USD("xrpusd"),
    BTC_USD("btcusd");

    private final String value;

    BitstampTradingPairEnum(String value) {
        this.value = value;
    }

    public static BitstampTradingPairEnum fromValue(String value) {
        switch (value.toLowerCase()) {
            case "xrpusd":
                return XRP_USD;
            case "btcusd":
                return BTC_USD;
            default:
                return INVALID_TRADING_PAIR;
        }
    }

    public static String extractValue(String value) {
        for (BitstampTradingPairEnum tradingPairEnum : BitstampTradingPairEnum.values()) {
            if (value.contains(tradingPairEnum.getValue())) {
                return tradingPairEnum.getValue();
            }
        }
        return INVALID_TRADING_PAIR.getValue();
    }

    public String getValue() {
        return value;
    }
}
