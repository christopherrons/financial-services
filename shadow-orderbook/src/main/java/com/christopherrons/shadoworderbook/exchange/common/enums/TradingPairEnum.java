package com.christopherrons.shadoworderbook.exchange.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TradingPairEnum {
    INVALID_TRADING_PAIR(ExchangeEnum.INVALID_EXCHANGE, "Invalid Trading Pair"),
    XRP_USD(ExchangeEnum.BITSTAMP, "xrpusd"),
    BTC_USD(ExchangeEnum.BITSTAMP, "btcusd");

    private final ExchangeEnum exchangeEnum;
    private final String name;

    TradingPairEnum(ExchangeEnum exchangeEnum, String name) {
        this.exchangeEnum = exchangeEnum;
        this.name = name;
    }

    public ExchangeEnum getExchangeEnum() {
        return exchangeEnum;
    }

    public String getName() {
        return name;
    }

    public static TradingPairEnum fromTradingPair(final String tradingPair, final ExchangeEnum exchangeEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(exchangeEnum)) {
            if (tradingPair.toLowerCase().equals(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static TradingPairEnum inferTradingPairEnum(final String tradingPair, final ExchangeEnum exchangeEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(exchangeEnum)) {
            if (tradingPair.contains(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static List<String> getAvailableExchangeTradingPairs(final ExchangeEnum exchangeEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(exchangeEnum))
                .map(TradingPairEnum::getName)
                .collect(Collectors.toList());
    }

    public static List<TradingPairEnum> getAvailableExchangeTradingPairEnums(final ExchangeEnum exchangeEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(exchangeEnum))
                .collect(Collectors.toList());
    }
}
