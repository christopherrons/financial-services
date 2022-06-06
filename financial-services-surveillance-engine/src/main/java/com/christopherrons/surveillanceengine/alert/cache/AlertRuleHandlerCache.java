package com.christopherrons.surveillanceengine.alert.cache;

import com.christopherrons.refdataengine.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.alert.model.AlertRuleHandler;
import com.christopherrons.tradingengine.orderbook.OrderbookService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlertRuleHandlerCache {

    private final StatisticsService statisticsService;
    private final OrderbookService orderbookService;

    public AlertRuleHandlerCache(StatisticsService statisticsService, OrderbookService orderbookService) {
        this.statisticsService = statisticsService;
        this.orderbookService = orderbookService;
    }
    private final Map<String, AlertRuleHandler> orderbookIdToAlertRule = new ConcurrentHashMap<>();

    public AlertRuleHandler findOrCreateAlertRule(final String orderbookId) {
        return orderbookIdToAlertRule.computeIfAbsent(orderbookId, k -> new AlertRuleHandler(orderbookId, statisticsService, orderbookService));
    }
}
