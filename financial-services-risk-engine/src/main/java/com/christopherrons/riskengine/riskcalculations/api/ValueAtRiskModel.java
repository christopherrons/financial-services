package com.christopherrons.riskengine.riskcalculations.api;

import com.christopherrons.common.model.risk.RiskCalculationData;
import com.christopherrons.common.model.risk.ValueAtRiskModelResult;

public interface ValueAtRiskModel {

    ValueAtRiskModelResult calculate(RiskCalculationData riskCalculationData);
}
