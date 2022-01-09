package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.OrderOperationEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;

public interface ExchangeOrder {

    OrderOperationEnum getOrderOperationEnum();

    ExchangeEnum getExchangeEnum();

    int getOrderType();

    long getOrderId();

    double getPrice();

    double getVolume();

    long getTimeStampInMs();

    TradingPairEnum getTradingPair();
}
