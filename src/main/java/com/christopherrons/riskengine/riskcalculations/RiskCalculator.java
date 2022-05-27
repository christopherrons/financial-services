package com.christopherrons.riskengine.riskcalculations;

import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollectionItem;
import com.christopherrons.refdata.portfolio.model.Portfolio;
import com.christopherrons.riskengine.riskcalculations.api.ValueAtRiskModel;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationData;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationResult;
import com.christopherrons.riskengine.riskcalculations.model.ValueAtRiskModelResult;
import com.christopherrons.riskengine.riskcalculations.riskmodels.ConditionalValueAtRiskParametric;
import com.christopherrons.riskengine.riskcalculations.riskmodels.ConditionalValueAtRiskHistorical;
import com.christopherrons.riskengine.riskcalculations.riskmodels.ConditionalValueAtRiskMonteCarlo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RiskCalculator {

    private final List<ValueAtRiskModel> valueAtRiskModels = List.of(
            new ConditionalValueAtRiskHistorical(),
            new ConditionalValueAtRiskMonteCarlo(),
            new ConditionalValueAtRiskParametric()
    );
    private final List<Portfolio> portfolios;
    private final PriceCollection priceCollection;

    public RiskCalculator(List<Portfolio> portfolios, PriceCollection priceCollection) {
        this.portfolios = portfolios;
        this.priceCollection = priceCollection;
    }

    public List<RiskCalculationResult> calculate() {
        List<RiskCalculationResult> riskCalculationResults = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            if (portfolio.isLiquidated()) {
                continue;
            }

            var riskCalculationData = new RiskCalculationData(portfolio, getInstrumentsInPortfolio(portfolio));
            riskCalculationResults.add(new RiskCalculationResult(
                            portfolio.getParticipant(),
                            runValueAtRiskModels(riskCalculationData)
                    )
            );
        }
        return riskCalculationResults;
    }

    private List<ValueAtRiskModelResult> runValueAtRiskModels(final RiskCalculationData riskCalculationData) {
        return valueAtRiskModels.stream()
                .map(model -> model.calculate(riskCalculationData))
                .toList();
    }

    private Map<String, PriceCollectionItem> getInstrumentsInPortfolio(final Portfolio portfolio) {
        Map<String, PriceCollectionItem> instrumentsInPortfolio = new ConcurrentHashMap<>();
        for (String instrumentId : portfolio.getInstrumentIds()) {
            instrumentsInPortfolio.put(instrumentId, priceCollection.getPriceCollectionFromInstrumentId(instrumentId));
        }
        return instrumentsInPortfolio;
    }
}



