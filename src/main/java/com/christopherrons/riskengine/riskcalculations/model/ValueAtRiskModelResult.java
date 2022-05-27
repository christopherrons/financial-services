package com.christopherrons.riskengine.riskcalculations.model;

import com.christopherrons.riskengine.riskcalculations.enums.ValueAtRiskCalculationEnum;

public record ValueAtRiskModelResult(ValueAtRiskCalculationEnum valueAtRiskCalculationEnum, double valueAtRiskDecimal, double confidenceLevel) {
}
