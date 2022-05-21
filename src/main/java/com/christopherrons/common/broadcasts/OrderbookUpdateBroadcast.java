package com.christopherrons.common.broadcasts;

import com.christopherrons.tradingengine.orderbook.model.OrderbookUpdate;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderbookUpdateBroadcast extends ApplicationEvent {

    private final Collection<OrderbookUpdate> orderbookUpdates;

    public OrderbookUpdateBroadcast(Object source, Collection<OrderbookUpdate> orderbookUpdates) {
        super(source);
        this.orderbookUpdates = orderbookUpdates;
    }

    public List<OrderbookUpdate> getOrderbookUpdates() {
        return new ArrayList<>(orderbookUpdates);
    }
}
