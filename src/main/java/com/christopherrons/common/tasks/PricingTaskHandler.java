package com.christopherrons.common.tasks;

import com.christopherrons.pricingengine.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class PricingTaskHandler {

    private static final Logger LOGGER = Logger.getLogger(PricingTaskHandler.class.getName());
    private final PricingService pricingService;

    @Autowired
    public PricingTaskHandler(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void scheduledPriceCollectionTask() {
        ScheduledExecutorService createPriceCollectionSchedules = Executors.newScheduledThreadPool(1);
        createPriceCollectionSchedules.scheduleAtFixedRate(() -> {
                    try {
                        pricingService.createPriceCollection();
                    } catch (IOException e) {
                        LOGGER.warning("Could not create price collection: " + e);
                    }
                },
                60, 60, TimeUnit.SECONDS);
    }
}
