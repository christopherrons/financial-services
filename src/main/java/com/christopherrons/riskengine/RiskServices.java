package com.christopherrons.riskengine;

import com.christopherrons.common.broadcasts.PriceCollectionsEventBroadcast;
import com.christopherrons.refdata.RefDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class RiskServices {

    private static final Logger LOGGER = Logger.getLogger(RiskServices.class.getName());

    @Autowired
    private RefDataService refDataService;

    @EventListener
    public void onPriceCollectionsEvent(PriceCollectionsEventBroadcast priceCollectionsEventBroadcast) {
        runMarginCalculations();
    }

    private void runMarginCalculations() {
        LOGGER.info("Run Margin Calculations.");
    }
}
