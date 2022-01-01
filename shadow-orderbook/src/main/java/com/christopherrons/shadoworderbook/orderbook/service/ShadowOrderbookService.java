package com.christopherrons.shadoworderbook.orderbook.service;


import com.christopherrons.shadoworderbook.exchange.api.ExchangeOrder;
import org.springframework.stereotype.Service;

@Service
public class ShadowOrderbookService {
    
    public void updateOrderbook(ExchangeOrder order) {
        System.out.println(order);
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
