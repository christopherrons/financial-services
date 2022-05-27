package com.christopherrons.surveillanceengine.alert.alertrules;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.alert.model.Alert;
import com.christopherrons.surveillanceengine.alert.model.Breach;
import com.christopherrons.surveillanceengine.api.AlertRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleSupplier;

public class LargeTradeTurnoverAlertRule implements AlertRule {

    private static final String ALERT_RULE_NAME = "Large Trade Turnover";
    private final StatisticsService statisticsService;
    private final String orderbookId;

    private class LargeTurnoverTurnoverBreachParameters {
        DoubleSupplier maxTurnOver = () -> 2 * statisticsService.getOrderbookStatistics(orderbookId).getAverageTurnOver();
    }

    private final LargeTurnoverTurnoverBreachParameters breachParameters = new LargeTurnoverTurnoverBreachParameters();

    public LargeTradeTurnoverAlertRule(String orderbookId, StatisticsService statisticsService) {
        this.orderbookId = orderbookId;
        this.statisticsService = statisticsService;
    }

    @Override
    public Alert investigate(MarketDataOrder order) {
        return new Alert(ALERT_RULE_NAME, orderbookId, false, Collections.emptyList());
    }

    @Override
    public Alert investigate(MarketDataTrade trade) {
        final double tradeTurnOver = trade.getTurnover();
        final double maxTurnOver = breachParameters.maxTurnOver.getAsDouble();
        if (tradeTurnOver > maxTurnOver) {
            List<Breach> breaches = new ArrayList<>();
            breaches.add(new Breach("turnoverTwiceAverage", String.valueOf(maxTurnOver), String.valueOf(tradeTurnOver)));
            return new Alert(ALERT_RULE_NAME, orderbookId, true, breaches);
        }
        return new Alert(ALERT_RULE_NAME, orderbookId, false, Collections.emptyList());
    }
}
