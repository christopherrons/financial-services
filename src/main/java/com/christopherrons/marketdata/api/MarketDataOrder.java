package com.christopherrons.marketdata.api;

import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;

public interface MarketDataOrder extends MarketDataEvent {

    OrderOperationEnum getOrderOperationEnum();

    int getOrderType();

    long getOrderId();

    double getPrice();

    double getVolume();
}
