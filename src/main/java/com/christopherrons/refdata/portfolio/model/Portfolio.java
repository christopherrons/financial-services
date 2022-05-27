package com.christopherrons.refdata.portfolio.model;

import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.riskengine.riskcalculations.enums.ValueAtRiskCalculationEnum;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Portfolio {

    private final Map<String, Position> instrumentIdToPosition = new TreeMap<>();
    private final Participant participant;
    private final ValueAtRiskCalculationEnum valueAtRiskCalculationEnum = ValueAtRiskCalculationEnum.HISTORICAL_CVAR;

    public Portfolio(Participant participant) {
        this.participant = participant;
    }

    public void updatePortfolio(final String instrumentId, final double volume, final boolean isBid) {
        Position position = instrumentIdToPosition.computeIfAbsent(instrumentId,
                k -> new Position(instrumentId));

        if (isBid) {
            position.increasePosition(volume);
        } else {
            position.decreasePosition(volume);
            if (position.getVolume() <= 0) {
                instrumentIdToPosition.remove(instrumentId);
            }
        }
    }

    public ValueAtRiskCalculationEnum getMarginCalculationEnum() {
        return valueAtRiskCalculationEnum;
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
