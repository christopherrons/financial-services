package com.christopherrons.surveillanceengine.alert;

import com.christopherrons.marketdata.MarketDataService;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.alert.cache.AlertRuleHandlerCache;
import com.christopherrons.surveillanceengine.alert.model.Alert;
import com.christopherrons.surveillanceengine.api.AlertRule;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AlertRuleService {
    private static final Logger LOGGER = Logger.getLogger(MarketDataService.class.getName());

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
