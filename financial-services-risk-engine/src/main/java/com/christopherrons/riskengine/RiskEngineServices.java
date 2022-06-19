package com.christopherrons.riskengine;

import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.common.broadcasts.RiskCalculationResultsBroadCast;
import com.christopherrons.common.model.pricing.PriceCollection;
import com.christopherrons.common.model.risk.RiskCalculationResult;
import com.christopherrons.refdataservice.RefDataService;
import com.christopherrons.riskengine.riskcalculations.RiskCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskEngineServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskEngineServices.class);
    @Autowired
    private RefDataService refDataService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void onPriceCollectionsEvent(PriceCollectionsEventBroadcast priceCollectionsEventBroadcast) {
        LOGGER.info("Price collection received, run risk calculations.");
        runRiskCalculations(priceCollectionsEventBroadcast.getPriceCollection());
    }

    private void runRiskCalculations(final PriceCollection priceCollection) {
        try {
            RiskCalculator riskCalculator = new RiskCalculator(refDataService.getPortfolios(), priceCollection);
            List<RiskCalculationResult> riskCalculationResults = riskCalculator.calculate();
            broadCastMarginCalculationResult(riskCalculationResults);
        } catch (Exception e) {
            LOGGER.warn("Could not run margin calculations: " + e);
        }
    }

    private void broadCastMarginCalculationResult(final List<RiskCalculationResult> riskCalculationResults) {
        if (!riskCalculationResults.isEmpty()) {
            applicationEventPublisher.publishEvent(new RiskCalculationResultsBroadCast(this, riskCalculationResults));
            LOGGER.info(String.format("Broadcasting % srisk calculation results", riskCalculationResults.size()));
        }
        LOGGER.info(String.format("Risk calculation results empty, no broadcast sent.", riskCalculationResults.size()));
    }

}
