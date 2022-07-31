package com.christopherrons.common.broadcasts;

import com.christopherrons.common.model.trading.OrderbookSnapshot;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderbookSnapshotBroadcast extends ApplicationEvent {

    private final transient Collection<OrderbookSnapshot> orderbookSnapshots;

    public OrderbookSnapshotBroadcast(Object source, Collection<OrderbookSnapshot> orderbookSnapshots) {
        super(source);
        this.orderbookSnapshots = orderbookSnapshots;
    }

    public List<OrderbookSnapshot> getOrderbookSnapshots() {
        return new ArrayList<>(orderbookSnapshots);
    }
}
