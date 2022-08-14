package com.christopherrons.riskengine.riskcalculations.riskmodels.derivatesmargin;

import com.christopherrons.common.model.refdata.Portfolio;
import com.christopherrons.common.model.refdata.Position;
import com.christopherrons.common.model.refdata.derivativesmargin.CombinedCommodity;
import com.christopherrons.common.model.refdata.derivativesmargin.DerivatesMarginRefData;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DerivativesMarginCalculator {

    private final ScanRiskCalculator scanRiskCalculator = new ScanRiskCalculator();

    public void calculate(final Portfolio portfolio, final DerivatesMarginRefData refData) {
        var combinedCommodityToPositions = mapPositionsToCombinedCommodity(portfolio.getPositions(), refData);
        scanRiskCalculator.calculateScanRisk(combinedCommodityToPositions);
    }

    private Map<CombinedCommodity, List<Position>> mapPositionsToCombinedCommodity(final Collection<Position> positions,
                                                                                   final DerivatesMarginRefData refData) {
        return positions.stream()
                .filter(position -> refData.isInstrumentIdMappableToCombinedCommodity(position.instrumentId()))
                .collect(Collectors.groupingBy(position -> refData.getCombinedCommodity(position.instrumentId())));

    }
}
