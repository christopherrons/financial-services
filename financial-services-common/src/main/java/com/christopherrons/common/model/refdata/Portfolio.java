package com.christopherrons.common.model.refdata;

import com.christopherrons.common.enums.risk.ValueAtRiskCalculationEnum;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Portfolio {

    private final Map<String, Position> instrumentIdToPosition = new TreeMap<>();
    private final Participant participant;
    private final ValueAtRiskCalculationEnum valueAtRiskCalculationEnum = ValueAtRiskCalculationEnum.MONTE_CARLO_CVAR;

    public Portfolio(Participant participant) {
        this.participant = participant;
    }

    public void updatePortfolio(final String instrumentId, final double volume, final boolean increasePosition) {
        Position position = instrumentIdToPosition.computeIfAbsent(instrumentId,
                k -> new Position(instrumentId));

        if (increasePosition) {
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
