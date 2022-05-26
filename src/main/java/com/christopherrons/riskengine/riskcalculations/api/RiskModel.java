package com.christopherrons.riskengine.riskcalculations.api;

import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationResult;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationData;

public interface RiskModel {

    RiskCalculationResult calculate(RiskCalculationData portfolio);
}
