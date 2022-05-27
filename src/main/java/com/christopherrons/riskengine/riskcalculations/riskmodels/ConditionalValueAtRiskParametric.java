package com.christopherrons.riskengine.riskcalculations.riskmodels;

import com.christopherrons.common.math.probability.ConfidenceLevelEnum;
import com.christopherrons.riskengine.riskcalculations.api.ValueAtRiskModel;
import com.christopherrons.riskengine.riskcalculations.enums.ValueAtRiskCalculationEnum;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationData;
import com.christopherrons.riskengine.riskcalculations.model.ValueAtRiskModelResult;
import org.apache.commons.math3.linear.RealVector;

public class ConditionalValueAtRiskParametric implements ValueAtRiskModel {

    private static final ConfidenceLevelEnum CONFIDENCE_LEVEL = ConfidenceLevelEnum.NINTY_FIVE;
    private static final ValueAtRiskCalculationEnum VALUE_AT_RISK_CALCULATION_ENUM = ValueAtRiskCalculationEnum.PARAMETRIC_CVAR;
    @Override
    public ValueAtRiskModelResult calculate(final RiskCalculationData riskCalculationData) {
        double scalars = CONFIDENCE_LEVEL.getZscore() / Math.sqrt(riskCalculationData.getNumberOfSamples());
        RealVector returnMeans = riskCalculationData.getPositionReturnMeans();
        RealVector returnStandardDeviations = riskCalculationData.getPositionReturnStd();
        RealVector percentileReturns = returnMeans.subtract(returnStandardDeviations.mapMultiply(scalars));
        RealVector positionWeights = riskCalculationData.getPositionWeights();
        double valueAtRiskDecimal = percentileReturns.dotProduct(positionWeights);
        return new ValueAtRiskModelResult(VALUE_AT_RISK_CALCULATION_ENUM, Math.abs(valueAtRiskDecimal), CONFIDENCE_LEVEL.getConfidenceLevel());
    }
}
