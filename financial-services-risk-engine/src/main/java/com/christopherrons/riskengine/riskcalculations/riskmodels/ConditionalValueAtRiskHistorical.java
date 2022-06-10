package com.christopherrons.riskengine.riskcalculations.riskmodels;

import com.christopherrons.common.enums.risk.ValueAtRiskCalculationEnum;
import com.christopherrons.common.math.probability.ConfidenceLevelEnum;
import com.christopherrons.common.model.risk.RiskCalculationData;
import com.christopherrons.common.model.risk.ValueAtRiskModelResult;
import com.christopherrons.riskengine.riskcalculations.api.ValueAtRiskModel;

import java.util.Collections;
import java.util.List;

public class ConditionalValueAtRiskHistorical implements ValueAtRiskModel {

    private static final double CONFIDENCE_LEVEL = ConfidenceLevelEnum.NINTY_FIVE.getConfidenceLevel();
    private static final ValueAtRiskCalculationEnum VALUE_AT_RISK_CALCULATION_ENUM = ValueAtRiskCalculationEnum.HISTORICAL_CVAR;

    @Override
    public ValueAtRiskModelResult calculate(final RiskCalculationData riskCalculationData) {
        double valueAtRiskDecimal = 0;
        for (String instrumentId : riskCalculationData.getPortfolioInstrumentIds()) {
            List<Double> relativeDailyReturns = riskCalculationData.getRelativeDailyReturns(instrumentId);
            Collections.sort(relativeDailyReturns);

            int indexOfPercentile = (int) ((1 - CONFIDENCE_LEVEL) * relativeDailyReturns.size());
            double expectedReturnUpToPercentile = relativeDailyReturns.subList(0, indexOfPercentile).stream().mapToDouble(r -> r).average().orElse(0);
            double positionWeight = riskCalculationData.calculatePositionWeight(instrumentId);

            valueAtRiskDecimal = valueAtRiskDecimal + expectedReturnUpToPercentile * positionWeight;
        }
        return new ValueAtRiskModelResult(VALUE_AT_RISK_CALCULATION_ENUM, Math.abs(valueAtRiskDecimal), CONFIDENCE_LEVEL);
    }
}
