package com.christopherrons.shadoworderbook.exchange.common.client;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeEvent;
import com.christopherrons.shadoworderbook.exchange.api.ExchangeOrder;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampEventDescriptionEnum;
import com.christopherrons.shadoworderbook.orderbook.service.ShadowOrderbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EventHandlerService {
    private static final Logger LOGGER = Logger.getLogger(EventHandlerService.class.getName());

    @Autowired
    private ShadowOrderbookService shadowOrderbookService;

    public void handleEvent(final ExchangeEvent event) {
        switch (event.getExchangeEnum()) {
            case BITSTAMP:
                handleBitstampEvent(event);
                break;
            default:
                break;
        }
    }

    private void handleBitstampEvent(final ExchangeEvent event) {
        switch ((BitstampEventDescriptionEnum) event.getEventDescriptionEnum()) {
            case ORDER_CREATED:
            case ORDER_DELETED:
            case ORDER_UPDATED:
                shadowOrderbookService.updateOrderbook((ExchangeOrder) event);
                break;
            case TRADE:
                break;
            case FORCED_RECONNECT:
                LOGGER.warning("Forced reconnect received!");
                break;
            case SUBSCRIPTION_SUCCEEDED:
        }
    }
}
