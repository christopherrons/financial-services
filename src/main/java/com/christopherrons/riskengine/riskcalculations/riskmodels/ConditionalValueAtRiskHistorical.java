package com.christopherrons.riskengine.riskcalculations.riskmodels;

import com.christopherrons.common.math.probability.ConfidenceLevelEnum;
import com.christopherrons.riskengine.riskcalculations.api.RiskModel;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationData;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationResult;

import java.util.Collections;
import java.util.List;

public class ConditionalValueAtRiskHistorical implements RiskModel {

    private static final double CONFIDENCE_LEVEL = ConfidenceLevelEnum.NINTY_FIVE.getConfidenceLevel();

    @Override
    public RiskCalculationResult calculate(final RiskCalculationData riskCalculationData) {
        double valueAtRiskDecimal = 0;
        if (riskCalculationData.getPortfolioInstrumentIds().size() > 1) {
            int a = 1;
        }
        for (String instrumentId : riskCalculationData.getPortfolioInstrumentIds()) {
            List<Double> relativeDailyReturns = riskCalculationData.getRelativeDailyReturns(instrumentId);
            Collections.sort(relativeDailyReturns);

            int index = (int) ((1 - CONFIDENCE_LEVEL) * relativeDailyReturns.size());
            double expectedReturnUpToPercentile = relativeDailyReturns.subList(0, index).stream().mapToDouble(r -> r).average().orElse(0);
            double positionWeight = riskCalculationData.calculatePositionWeight(instrumentId);

            valueAtRiskDecimal = valueAtRiskDecimal + expectedReturnUpToPercentile * positionWeight;
        }
        return new RiskCalculationResult(riskCalculationData.getParticipant(), Math.abs(valueAtRiskDecimal), CONFIDENCE_LEVEL);
    }


}
