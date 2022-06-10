package com.christopherrons.common.model.risk;

import com.christopherrons.common.model.refdata.Participant;

import java.util.List;

public record RiskCalculationResult(Participant participant, List<ValueAtRiskModelResult> valueAtRiskModelResults) {
}
