package com.christopherrons.marketdata.common.enums.subscription;

import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TradingPairEnum {
    INVALID_TRADING_PAIR(MargetDataFeedEnum.INVALID_DATA_FEED, "Invalid Trading Pair", CashCurrencyEnum.INVALID_CASH_CURRENCY, CryptoCurrency.INVALID_CASH_CURRENCY),
    XRP_USD(MargetDataFeedEnum.BITSTAMP, "xrpusd", CashCurrencyEnum.USD, CryptoCurrency.XRP),
    BTC_USD(MargetDataFeedEnum.BITSTAMP, "btcusd", CashCurrencyEnum.USD, CryptoCurrency.BTC);

    private final MargetDataFeedEnum margetDataFeedEnum;
    private final String name;
    private final CashCurrencyEnum cashCurrencyEnum;
    private final CryptoCurrency cryptoCurrencyEnum;

    TradingPairEnum(MargetDataFeedEnum margetDataFeedEnum, String name, CashCurrencyEnum cashCurrencyEnum, CryptoCurrency cryptoCurrencyEnum) {
        this.margetDataFeedEnum = margetDataFeedEnum;
        this.name = name;
        this.cashCurrencyEnum = cashCurrencyEnum;
        this.cryptoCurrencyEnum = cryptoCurrencyEnum;
    }

    public static TradingPairEnum fromTradingPair(final String tradingPair, final MargetDataFeedEnum margetDataFeedEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(margetDataFeedEnum)) {
            if (tradingPair.toLowerCase().equals(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static TradingPairEnum inferTradingPairEnum(final String tradingPair, final MargetDataFeedEnum margetDataFeedEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(margetDataFeedEnum)) {
            if (tradingPair.contains(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static List<String> getAvailableTradingPairsByDataFeed(final MargetDataFeedEnum margetDataFeedEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(margetDataFeedEnum))
                .map(TradingPairEnum::getName)
                .collect(Collectors.toList());
    }

    public static List<TradingPairEnum> getAvailableExchangeTradingPairEnums(final MargetDataFeedEnum margetDataFeedEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(margetDataFeedEnum))
                .collect(Collectors.toList());
    }

    public MargetDataFeedEnum getExchangeEnum() {
        return margetDataFeedEnum;
    }

    public String getName() {
        return name;
    }

    public CashCurrencyEnum getCashCurrencyEnum() {
        return cashCurrencyEnum;
    }

    public CryptoCurrency getCryptoCurrencyEnum() {
        return cryptoCurrencyEnum;
    }
}
