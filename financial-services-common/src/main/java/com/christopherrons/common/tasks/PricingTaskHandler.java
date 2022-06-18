package com.christopherrons.common.tasks;

import com.christopherrons.common.broadcasts.TriggerPriceCollectionBroadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class PricingTaskHandler {

    private static final Logger LOGGER = Logger.getLogger(PricingTaskHandler.class.getName());

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void scheduledPriceCollectionTask() {
        ScheduledExecutorService createPriceCollectionSchedules = Executors.newScheduledThreadPool(1);
        createPriceCollectionSchedules.scheduleAtFixedRate(() -> {
                    try {
                        applicationEventPublisher.publishEvent(new TriggerPriceCollectionBroadcast(this));
                    } catch (Exception e) {
                        LOGGER.warning("Could not create price collection: " + e);
                    }
                },
                5, 60, TimeUnit.SECONDS);
    }
}
