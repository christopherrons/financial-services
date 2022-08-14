package com.christopherrons.riskengine.riskcalculations.riskmodels.derivatesmargin;

import com.christopherrons.common.model.refdata.Position;
import com.christopherrons.common.model.refdata.derivativesmargin.CombinedCommodity;
import com.christopherrons.common.model.refdata.derivativesmargin.spanfile.pointintime.clearingorg.exchange.RiskArray;

import java.util.List;
import java.util.Map;

import static org.apache.commons.math3.stat.StatUtils.max;

public class ScanRiskCalculator {

    private final int nrOfScenarios = 16;

    public double calculateScanRisk(final Map<CombinedCommodity, List<Position>> combinedCommodityToPositions) {
        double totalScanRisk = 0;
        for (var ccToPositions : combinedCommodityToPositions.entrySet()) {
            totalScanRisk = totalScanRisk + calculateScanRisk(ccToPositions.getKey(), ccToPositions.getValue());
        }

        return totalScanRisk;
    }

    private double calculateScanRisk(final CombinedCommodity combinedCommodity, final List<Position> positions) {
        final double[] aggregatedScenarioScanRisk = new double[nrOfScenarios];
        for (Position position : positions) {
            RiskArray riskArray = combinedCommodity.getRiskArray(position.instrumentId());
            double[] instrumentScenarioLosses = riskArray.getScenarioLosses();
            for (int scenario = 0; scenario < nrOfScenarios; scenario++) {
                aggregatedScenarioScanRisk[scenario] = aggregatedScenarioScanRisk[scenario] + position.volume() * instrumentScenarioLosses[scenario];
            }
        }

        return max(aggregatedScenarioScanRisk);
    }
}
