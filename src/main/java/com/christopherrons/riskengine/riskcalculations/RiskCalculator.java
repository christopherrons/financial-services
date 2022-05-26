package com.christopherrons.riskengine.riskcalculations;

import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollectionItem;
import com.christopherrons.refdata.portfolio.model.Portfolio;
import com.christopherrons.riskengine.riskcalculations.riskmodels.ConditionalValueAtRiskHistorical;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationResult;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RiskCalculator {

    private final ConditionalValueAtRiskHistorical valueAtRiskHistorical = new ConditionalValueAtRiskHistorical();
    private final List<Portfolio> portfolios;
    private final PriceCollection priceCollection;

    public RiskCalculator(List<Portfolio> portfolios, PriceCollection priceCollection) {
        this.portfolios = portfolios;
        this.priceCollection = priceCollection;
    }

    public List<RiskCalculationResult> calculate() {
        List<RiskCalculationResult> riskCalculationResults = new ArrayList<>();
        RiskCalculationResult result;
        for (Portfolio portfolio : portfolios) {
            var riskPortfolio = new RiskCalculationData(portfolio, getInstrumentsInPortfolio(portfolio));
            switch (portfolio.getMarginCalculationEnum()) {
                case HISTORICAL_CVAR:
                default:
                    result = valueAtRiskHistorical.calculate(riskPortfolio);
            }
            riskCalculationResults.add(result);
        }
        return riskCalculationResults;
    }

    private Map<String, PriceCollectionItem> getInstrumentsInPortfolio(final Portfolio portfolio) {
        Map<String, PriceCollectionItem> instrumentsInPortfolio = new ConcurrentHashMap<>();
        for (String instrumentId : portfolio.getInstrumentIds()) {
            instrumentsInPortfolio.put(instrumentId, priceCollection.getPriceCollectionFromInstrumentId(instrumentId));
        }
        return instrumentsInPortfolio;
    }
}



