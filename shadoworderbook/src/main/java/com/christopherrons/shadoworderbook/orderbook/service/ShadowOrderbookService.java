package com.christopherrons.shadoworderbook.orderbook.service;


import com.christopherrons.shadoworderbook.exchange.api.ExchangeOrder;
import com.christopherrons.shadoworderbook.orderbook.cache.ShadowOrderbookCache;
import com.christopherrons.shadoworderbook.orderbook.model.ShadowOrderbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShadowOrderbookService {

    @Autowired
    private ShadowOrderbookCache shadowOrderbookCache;

    public void updateOrderbook(ExchangeOrder order) {
        final ShadowOrderbook shadowOrderbook = shadowOrderbookCache.findOrCreate(order.getExchangeEnum().getName(), order.getTradingPair().getName());
        switch (order.getOrderOperationEnum()) {
            case UPDATE:
                break;
            case CREATE:
                break;
            case DELETE:
                break;
            default:
                break;
        }

    }

}
