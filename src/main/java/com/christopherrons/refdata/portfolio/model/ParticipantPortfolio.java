package com.christopherrons.refdata.portfolio.model;

import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.participant.model.Participant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParticipantPortfolio {

    private final Map<Instrument, Position> instrumentToPosition = new ConcurrentHashMap<>();
    private final Participant participant;

    public ParticipantPortfolio(Participant participant) {
        this.participant = participant;
    }

    public void updatePortfolio(final MarketDataTrade trade) {
        Position position = instrumentToPosition.computeIfAbsent(trade.getInstrument(),
                k -> new Position(trade.getInstrument(), trade.getVolume()));

        if (trade.getBidParticipant().equals(participant)) {
            position.increasePosition(trade.getVolume());
        } else {
            position.decreasePosition(trade.getVolume());
        }
    }
}
