package com.christopherrons.shadoworderbook.exchange.api;

import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.OrderOperationEnum;

public interface ExchangeOrder {

    OrderOperationEnum getOrderOperationEnum();

    ExchangeEnum getExchangeEnum();

    int getOrderType();

    long getOrderId();

    double getPrice();

    double getVolume();

    long getTimeStampInMs();

    String getTradingPair();


}
