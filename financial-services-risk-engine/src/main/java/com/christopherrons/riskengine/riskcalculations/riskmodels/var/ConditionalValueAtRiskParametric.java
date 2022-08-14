package com.christopherrons.riskengine.riskcalculations.riskmodels.var;

import com.christopherrons.common.enums.risk.ValueAtRiskCalculationEnum;
import com.christopherrons.common.math.probability.ConfidenceLevelEnum;
import com.christopherrons.common.model.risk.RiskCalculationData;
import com.christopherrons.common.model.risk.ValueAtRiskModelResult;
import com.christopherrons.riskengine.riskcalculations.api.ValueAtRiskModel;
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
