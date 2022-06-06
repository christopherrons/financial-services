package com.christopherrons.surveillanceengine.alert.model;

import com.christopherrons.refdataengine.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.alert.alertrules.LargeOrderValueAlertRule;
import com.christopherrons.surveillanceengine.alert.alertrules.LargeTradeTurnoverAlertRule;
import com.christopherrons.surveillanceengine.api.AlertRule;
import com.christopherrons.tradingengine.orderbook.OrderbookService;

import java.util.List;

public class AlertRuleHandler {

    private final StatisticsService statisticsService;
    private final OrderbookService orderbookService;
    private final String orderbookId;
    private final List<AlertRule> alertRules;

    public AlertRuleHandler(String orderbookId, StatisticsService statisticsService, OrderbookService orderbookService) {
        this.orderbookId = orderbookId;
        this.statisticsService = statisticsService;
        this.orderbookService = orderbookService;
        this.alertRules = List.of(
                new LargeTradeTurnoverAlertRule(orderbookId, statisticsService),
                new LargeOrderValueAlertRule(orderbookId, statisticsService)
        );
    }

    public List<AlertRule> getAlertRules() {
        return alertRules;
    }
}
