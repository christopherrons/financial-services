package com.christopherrons.shadoworderbook.exchange.common.service;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.api.ExchangeOrder;
import com.christopherrons.shadoworderbook.exchange.api.ExchangeSubscription;
import com.christopherrons.shadoworderbook.exchange.common.cache.WebsocketSubscriptionCache;
import com.christopherrons.shadoworderbook.orderbook.service.ShadowOrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EventHandlerService {
    private static final Logger LOGGER = Logger.getLogger(EventHandlerService.class.getName());

    @Autowired
    private ShadowOrderbookService shadowOrderbookService;

    @Autowired
    private WebsocketSubscriptionCache websocketSubscriptionCache;

    public void handleEvent(final ExchangeEvent event) {
        switch (event.getEventTypeEnum()) {
            case ORDER:
                shadowOrderbookService.updateOrderbook((ExchangeOrder) event);
                break;
            case TRADE:
                break;
        }
    }
}
