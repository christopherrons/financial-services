package com.christopherrons.common.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ScheduleTaskService {

    private static final Logger LOGGER = Logger.getLogger(ScheduleTaskService.class.getName());
    private final PricingTaskHandler pricingTaskHandler;

    @Autowired
    public ScheduleTaskService(PricingTaskHandler pricingTaskHandler) {
        this.pricingTaskHandler = pricingTaskHandler;

        init();
    }

    private void init() {
        scheduleCreatePriceCollection();
    }

    private void scheduleCreatePriceCollection() {
        pricingTaskHandler.scheduledPriceCollectionTask();
    }
}
