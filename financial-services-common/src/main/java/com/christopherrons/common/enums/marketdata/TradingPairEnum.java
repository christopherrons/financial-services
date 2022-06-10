package com.christopherrons.common.enums.marketdata;

import java.util.Arrays;
import java.util.List;

public enum TradingPairEnum {
    INVALID_TRADING_PAIR(MarketDataFeedEnum.INVALID_DATA_FEED, "Invalid Trading Pair", CashCurrencyEnum.INVALID_CASH_CURRENCY, CryptoCurrency.INVALID_CASH_CURRENCY),
    XRP_USD(MarketDataFeedEnum.BITSTAMP, "xrpusd", CashCurrencyEnum.USD, CryptoCurrency.XRP),
    BTC_USD(MarketDataFeedEnum.BITSTAMP, "btcusd", CashCurrencyEnum.USD, CryptoCurrency.BTC);

    private final MarketDataFeedEnum marketDataFeedEnum;
    private final String name;
    private final CashCurrencyEnum cashCurrencyEnum;
    private final CryptoCurrency cryptoCurrencyEnum;

    TradingPairEnum(MarketDataFeedEnum marketDataFeedEnum, String name, CashCurrencyEnum cashCurrencyEnum, CryptoCurrency cryptoCurrencyEnum) {
        this.marketDataFeedEnum = marketDataFeedEnum;
        this.name = name;
        this.cashCurrencyEnum = cashCurrencyEnum;
        this.cryptoCurrencyEnum = cryptoCurrencyEnum;
    }

    public static TradingPairEnum fromTradingPair(final String tradingPair, final MarketDataFeedEnum marketDataFeedEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(marketDataFeedEnum)) {
            if (tradingPair.toLowerCase().equals(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static TradingPairEnum inferTradingPairEnum(final String tradingPair, final MarketDataFeedEnum marketDataFeedEnum) {
        for (TradingPairEnum tradingPairEnum : getAvailableExchangeTradingPairEnums(marketDataFeedEnum)) {
            if (tradingPair.contains(tradingPairEnum.getName())) {
                return tradingPairEnum;
            }
        }
        return INVALID_TRADING_PAIR;
    }

    public static List<String> getAvailableTradingPairsByDataFeed(final MarketDataFeedEnum marketDataFeedEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(marketDataFeedEnum))
                .map(TradingPairEnum::getName)
                .toList();
    }

    public static List<TradingPairEnum> getAvailableExchangeTradingPairEnums(final MarketDataFeedEnum marketDataFeedEnum) {
        return Arrays.stream(TradingPairEnum.values())
                .filter(tradingPairEnum -> !tradingPairEnum.equals(TradingPairEnum.INVALID_TRADING_PAIR))
                .filter(tradingPairEnum -> tradingPairEnum.getExchangeEnum().equals(marketDataFeedEnum))
                .toList();
    }

    public MarketDataFeedEnum getExchangeEnum() {
        return marketDataFeedEnum;
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
