package com.christopherrons.riskengine.riskcalculations.riskmodels;

import com.christopherrons.common.enums.risk.ValueAtRiskCalculationEnum;
import com.christopherrons.common.math.probability.ConfidenceLevelEnum;
import com.christopherrons.common.model.risk.RiskCalculationData;
import com.christopherrons.common.model.risk.ValueAtRiskModelResult;
import com.christopherrons.riskengine.riskcalculations.api.ValueAtRiskModel;
import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


public class ConditionalValueAtRiskMonteCarlo implements ValueAtRiskModel {

    private static final double CONFIDENCE_LEVEL = ConfidenceLevelEnum.NINTY_FIVE.getConfidenceLevel();
    private static final ValueAtRiskCalculationEnum VALUE_AT_RISK_CALCULATION_ENUM = ValueAtRiskCalculationEnum.MONTE_CARLO_CVAR;

    @Override
    public ValueAtRiskModelResult calculate(final RiskCalculationData riskCalculationData) {
        List<Double> simulatedValueAtRisk = runSimulation(
                riskCalculationData.getReturnDistribution(),
                riskCalculationData.getPositionWeights().toArray());
        Collections.sort(simulatedValueAtRisk);

        int indexOfPercentile = (int) ((1 - CONFIDENCE_LEVEL) * simulatedValueAtRisk.size());
        double expectedValueAtRiskUpToPercentile = simulatedValueAtRisk.subList(0, indexOfPercentile).stream().mapToDouble(r -> r).average().orElse(0);

        return new ValueAtRiskModelResult(VALUE_AT_RISK_CALCULATION_ENUM, Math.abs(expectedValueAtRiskUpToPercentile), CONFIDENCE_LEVEL);
    }

    private List<Double> runSimulation(final MultivariateNormalDistribution returnDistribution, final double[] weights) {
        int numberOfIterations = 5000;
        List<Double> simulatedValueAtRisk = new ArrayList<>();
        while (simulatedValueAtRisk.size() < numberOfIterations) {
            double[] sampleReturns = returnDistribution.sample();
            double sampleValueAtRisk = IntStream.range(0, sampleReturns.length).mapToDouble(index -> sampleReturns[index] * weights[index]).sum();
            simulatedValueAtRisk.add(sampleValueAtRisk);
        }
        return simulatedValueAtRisk;
    }
}