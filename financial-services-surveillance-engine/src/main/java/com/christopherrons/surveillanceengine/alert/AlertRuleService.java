package com.christopherrons.surveillanceengine.alert;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.model.surveillance.Alert;
import com.christopherrons.refdataservice.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.alert.cache.AlertRuleHandlerCache;
import com.christopherrons.surveillanceengine.api.AlertRule;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

@Service
public class AlertRuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertRuleService.class);

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private OrderbookService orderbookService;
    private final AlertRuleHandlerCache alertRuleHandlerCache;


    public AlertRuleService(StatisticsService statisticsService, OrderbookService orderbookService) {
        this.statisticsService = statisticsService;
        this.orderbookService = orderbookService;
        this.alertRuleHandlerCache = new AlertRuleHandlerCache(statisticsService, orderbookService);
    }

    public List<Alert> investigate(final MarketDataOrder order) {
        List<Alert> triggeredAlerts = new ArrayList<>();
        for (AlertRule alertRule : alertRuleHandlerCache.findOrCreateAlertRule(order.getOrderbookId()).getAlertRules()) {
            Alert alert = alertRule.investigate(order);
            if (alert.isTriggered()) {
                LOGGER.info(String.format("Alert triggered: %s", alert));
                triggeredAlerts.add(alert);
            }
        }
        return triggeredAlerts;
    }

    public List<Alert> investigate(final MarketDataTrade trade) {
        List<Alert> triggeredAlerts = new ArrayList<>();
        for (AlertRule alertRule : alertRuleHandlerCache.findOrCreateAlertRule(trade.getOrderbookId()).getAlertRules()) {
            Alert alert = alertRule.investigate(trade);
            if (alert.isTriggered()) {
                LOGGER.info(String.format("Alert triggered: %s", alert));
                triggeredAlerts.add(alert);
            }
        }
        return triggeredAlerts;
    }
}
