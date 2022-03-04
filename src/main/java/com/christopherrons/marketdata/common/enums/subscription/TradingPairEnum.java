package com.christopherrons.marketdata.common.enums.subscription;

import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TradingPairEnum {
    INVALID_TRADING_PAIR(MargetDataEnum.INVALID_EXCHANGE, "Invalid Trading Pair", CashCurrencyEnum.INVALID_CASH_CURRENCY, CryptoCurrency.INVALID_CASH_CURRENCY),
    XRP_USD(MargetDataEnum.BITSTAMP, "xrpusd", CashCurrencyEnum.USD, CryptoCurrency.XRP),
    BTC_USD(MargetDataEnum.BITSTAMP, "btcusd", CashCurrencyEnum.USD, CryptoCurrency.BTC);

    private final MargetDataEnum margetDataEnum;
    private final String name;
    private final CashCurrencyEnum cashCurrencyEnum;
    private final CryptoCurrency cryptoCurrencyEnum;

    TradingPairEnum(MargetDataEnum margetDataEnum, String name, CashCurrencyEnum cashCurrencyEnum, CryptoCurrency cryptoCurrencyEnum) {
        this.margetDataEnum = margetDataEnum;
        this.name = name;
        this.cashCurrencyEnum = cashCurrencyEnum;
        this.cryptoCurrencyEnum = cryptoCurrencyEnum;
    }

    public MargetDataEnum getExchangeEnum() {
        return margetDataEnum;
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

    public static TradingPairEnum fromTradingPair(final String tradingPair, final MargetDataEnum margetDataEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(margetDataEnum)) {
            if (tradingPair.toLowerCase().equals(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static TradingPairEnum inferTradingPairEnum(final String tradingPair, final MargetDataEnum margetDataEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(margetDataEnum)) {
            if (tradingPair.contains(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static List<String> getAvailableExchangeTradingPairs(final MargetDataEnum margetDataEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(margetDataEnum))
                .map(TradingPairEnum::getName)
                .collect(Collectors.toList());
    }

    public static List<TradingPairEnum> getAvailableExchangeTradingPairEnums(final MargetDataEnum margetDataEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(margetDataEnum))
                .collect(Collectors.toList());
    }
}
