package com.christopherrons.riskengine;

import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.common.broadcasts.RiskCalculationResultsBroadCast;
import com.christopherrons.common.model.pricing.PriceCollection;
import com.christopherrons.common.model.risk.RiskCalculationResult;
import com.christopherrons.refdataengine.RefDataService;
import com.christopherrons.riskengine.riskcalculations.RiskCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RiskEngineServices {

    private static final Logger LOGGER = Logger.getLogger(RiskEngineServices.class.getName());
    @Autowired
    private RefDataService refDataService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @EventListener
    public void onPriceCollectionsEvent(PriceCollectionsEventBroadcast priceCollectionsEventBroadcast) {
        runMarginCalculations(priceCollectionsEventBroadcast.getPriceCollection());
    }

    private void runMarginCalculations(final PriceCollection priceCollection) {
        try {
            LOGGER.info("Run margin calculations.");
            RiskCalculator riskCalculator = new RiskCalculator(refDataService.getPortfolios(), priceCollection);
            List<RiskCalculationResult> riskCalculationResults = riskCalculator.calculate();
            broadCastMarginCalculationResult(riskCalculationResults);
        } catch (Exception e) {
            LOGGER.warning("Could not run margin calculations: " + e);
        }
    }

    private void broadCastMarginCalculationResult(final List<RiskCalculationResult> riskCalculationResults) {
        if (!riskCalculationResults.isEmpty()) {
            applicationEventPublisher.publishEvent(new RiskCalculationResultsBroadCast(this, riskCalculationResults));
        }
    }

}
