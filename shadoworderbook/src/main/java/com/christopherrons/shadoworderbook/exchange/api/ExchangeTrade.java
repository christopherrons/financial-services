package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;

public interface ExchangeTrade {

    ExchangeEnum getExchangeEnum();

    long getTradeId();

    long getSellOrderId();

    long getBuyOrderId();

    double getPrice();

    double getVolume();

    long getTimeStampInMs();

    TradingPairEnum getTradingPair();

    int getSideAggressor();
}
