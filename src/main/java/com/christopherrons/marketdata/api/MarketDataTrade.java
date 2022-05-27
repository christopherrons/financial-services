package com.christopherrons.marketdata.api;

import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.refdata.participant.model.User;

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
