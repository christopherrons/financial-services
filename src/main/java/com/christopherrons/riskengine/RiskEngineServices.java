package com.christopherrons.riskengine;

import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.pricingengine.pricecollection.model.PriceCollection;
import com.christopherrons.refdata.RefDataService;
import com.christopherrons.riskengine.riskcalculations.RiskCalculator;
import com.christopherrons.riskengine.riskcalculations.model.RiskCalculationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RiskEngineServices {

    private static final Logger LOGGER = Logger.getLogger(RiskEngineServices.class.getName());

    @Autowired
    private RefDataService refDataService;

    @EventListener
    public void onPriceCollectionsEvent(PriceCollectionsEventBroadcast priceCollectionsEventBroadcast) {
        runMarginCalculations(priceCollectionsEventBroadcast.getPriceCollection());
    }

    private void runMarginCalculations(final PriceCollection priceCollection) {
        try {
            LOGGER.info("Run margin calculations.");
            RiskCalculator riskCalculator = new RiskCalculator(refDataService.getPortfolios(), priceCollection);
            List<RiskCalculationResult> riskCalculationResults = riskCalculator.calculate();
        } catch (Exception e) {
            LOGGER.warning("Could not run margin calculations: " + e);
        }

    }
}
