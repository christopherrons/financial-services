package com.christopherrons.riskengine.riskcalculations.model;

import com.christopherrons.refdata.participant.model.Participant;

public record RiskCalculationResult(Participant participant, double valueAtRiskDecimal, double confidenceLevel) {
}
