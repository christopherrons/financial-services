package com.christopherrons.marketdata.api;

import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.refdata.participant.model.Participant;

public interface MarketDataOrder extends MarketDataEvent {

    OrderOperationEnum getOrderOperationEnum();

    int getOrderType();

    long getOrderId();

    double getPrice();

    double getInitialVolume();

    double getCurrentVolume();

    Participant getParticipant();

    boolean isFilled();

}
