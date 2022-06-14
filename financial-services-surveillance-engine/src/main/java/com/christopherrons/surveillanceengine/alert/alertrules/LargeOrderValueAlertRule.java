package com.christopherrons.surveillanceengine.alert.alertrules;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.model.surveillance.Alert;
import com.christopherrons.common.model.surveillance.Breach;
import com.christopherrons.refdataservice.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.api.AlertRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleSupplier;

public class LargeOrderValueAlertRule implements AlertRule {
    private static final String ALERT_RULE_NAME = "Large Order Value";
    private final StatisticsService statisticsService;
    private final String orderbookId;

    private class LargeOrderValueBreachParameters {
        DoubleSupplier maxOrderValue = () -> 2 * statisticsService.getOrderbookStatistics(orderbookId).getAverageOrderValue();
    }

    private final LargeOrderValueBreachParameters breachParameters = new LargeOrderValueBreachParameters();

    public LargeOrderValueAlertRule(String orderbookId, StatisticsService statisticsService) {
        this.orderbookId = orderbookId;
        this.statisticsService = statisticsService;
    }

    @Override
    public Alert investigate(MarketDataTrade trade) {
        return new Alert(ALERT_RULE_NAME, orderbookId, false, Collections.emptyList());
    }

    @Override
    public Alert investigate(MarketDataOrder order) {
        final double orderValue = order.getOrderValue();
        final double maxOrderValue = breachParameters.maxOrderValue.getAsDouble();
        if (orderValue > maxOrderValue) {
            List<Breach> breaches = new ArrayList<>();
            breaches.add(new Breach("turnoverTwiceAverage", String.valueOf(maxOrderValue), String.valueOf(orderValue)));
            return new Alert(ALERT_RULE_NAME, orderbookId, true, breaches);
        }
        return new Alert(ALERT_RULE_NAME, orderbookId, false, Collections.emptyList());
    }
}
