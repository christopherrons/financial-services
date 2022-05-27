package com.christopherrons.surveillanceengine;

import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.broadcasts.TriggeredAlertsBroadcast;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.statistics.StatisticsService;
import com.christopherrons.surveillanceengine.alert.AlertRuleService;
import com.christopherrons.surveillanceengine.alert.model.Alert;
import com.christopherrons.tradingengine.orderbook.OrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveillanceEngineService {

    @Autowired
    private AlertRuleService alertRuleService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @EventListener
    public void onOrderEvent(OrderEventBroadcast event) {
        List<Alert> triggeredAlerts = new ArrayList<>();
        for (MarketDataOrder order : event.getOrders()) {
            List<Alert> alerts = alertRuleService.investigate(order);
            if (alerts.isEmpty()) {
                continue;
            }
            triggeredAlerts.addAll(alerts);
        }
        broadCastTriggeredAlerts(triggeredAlerts);
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast tradeEventBroadcast) {
        List<Alert> triggeredAlerts = new ArrayList<>();
        for (MarketDataTrade trade : tradeEventBroadcast.getTrades()) {
            List<Alert> alerts = alertRuleService.investigate(trade);
            if (alerts.isEmpty()) {
                continue;
            }
            triggeredAlerts.addAll(alerts);
        }
        broadCastTriggeredAlerts(triggeredAlerts);
    }

    private void broadCastTriggeredAlerts(final List<Alert> triggeredAlerts) {
        if (!triggeredAlerts.isEmpty()) {
            applicationEventPublisher.publishEvent(new TriggeredAlertsBroadcast(this, triggeredAlerts));
        }
    }
}
