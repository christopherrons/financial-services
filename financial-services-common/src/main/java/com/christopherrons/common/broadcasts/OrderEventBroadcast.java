package com.christopherrons.common.broadcasts;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class OrderEventBroadcast extends ApplicationEvent {

    private final transient List<MarketDataOrder> orders;

    public OrderEventBroadcast(Object source, List<MarketDataOrder> orders) {
        super(source);
        this.orders = orders;
    }


    public List<MarketDataOrder> getOrders() {
        return orders;
    }
}
