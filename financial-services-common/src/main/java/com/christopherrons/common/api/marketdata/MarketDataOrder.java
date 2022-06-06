package com.christopherrons.common.api.marketdata;

import com.christopherrons.common.enums.marketdata.OrderOperationEnum;
import com.christopherrons.common.model.refdata.Participant;

public interface MarketDataOrder extends MarketDataEvent {

    OrderOperationEnum getOrderOperationEnum();

    int getOrderType();

    long getOrderId();

    double getPrice();

    double getInitialVolume();

    double getCurrentVolume();

    Participant getParticipant();

    boolean isFilled();

    double getOrderValue();

}
