package com.christopherrons.common.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTaskService.class);
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
