package com.christopherrons.common.model.risk;

import com.christopherrons.common.enums.risk.ValueAtRiskCalculationEnum;

public record ValueAtRiskModelResult(ValueAtRiskCalculationEnum valueAtRiskCalculationEnum, double valueAtRiskDecimal, double confidenceLevel) {
}
