package com.christopherrons.common.model.refdata;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Portfolio {

    private final Map<String, Position> instrumentIdToPosition = new TreeMap<>();
    private final Participant participant;

    public Portfolio(Participant participant) {
        this.participant = participant;
    }

    public void updatePortfolio(final String instrumentId, final double volume) {
        double positionVolume = 0;
        if (instrumentIdToPosition.containsKey(instrumentId)) {
            positionVolume = instrumentIdToPosition.remove(instrumentId).volume();
        }
        var position = new Position(instrumentId, volume + positionVolume);
        if (position.volume() > 0) {
            instrumentIdToPosition.put(instrumentId, position);
        }
    }

    public Set<String> getInstrumentIds() {
        return instrumentIdToPosition.keySet();
    }

    public Collection<Position> getPositions() {
        return instrumentIdToPosition.values();
    }

    public Participant getParticipant() {
        return participant;
    }

    public Map<String, Position> getInstrumentIdToPosition() {
        return instrumentIdToPosition;
    }

    public boolean isLiquidated() {
        return instrumentIdToPosition.isEmpty();
    }
}
