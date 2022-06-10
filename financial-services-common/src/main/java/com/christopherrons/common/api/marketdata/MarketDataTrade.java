package com.christopherrons.common.api.marketdata;

import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.common.model.refdata.User;

public interface MarketDataTrade extends MarketDataEvent {

    long getTradeId();

    long getSellOrderId();

    long getBuyOrderId();

    double getPrice();

    double getVolume();

    boolean isBidSideAggressor();

    User tradeAggressorUser();

    Participant getAskParticipant();

    Participant getBidParticipant();

    double getTurnover();
}
