package com.christopherrons.marketdata.api;

import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;

public interface MarketDataOrder extends MarketDataEvent {

    OrderOperationEnum getOrderOperationEnum();

    int getOrderType();

    long getOrderId();

    double getPrice();

    double getVolume();
}
