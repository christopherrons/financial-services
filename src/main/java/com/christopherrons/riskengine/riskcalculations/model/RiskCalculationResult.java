package com.christopherrons.riskengine.riskcalculations.model;

import com.christopherrons.refdata.participant.model.Participant;

import java.util.List;

public record RiskCalculationResult(Participant participant, List<ValueAtRiskModelResult> valueAtRiskModelResults) {
}
