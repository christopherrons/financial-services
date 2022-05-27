package com.christopherrons.riskengine.riskcalculations.api;

import com.christopherrons.riskengine.riskcalculations.model.ValueAtRiskModelResult;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationData;

public interface ValueAtRiskModel {

    ValueAtRiskModelResult calculate(RiskCalculationData riskCalculationData);
}
