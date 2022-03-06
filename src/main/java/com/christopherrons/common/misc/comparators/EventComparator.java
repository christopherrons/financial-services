package com.christopherrons.common.misc.comparators;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;

import java.util.Comparator;

public class EventComparator<T extends MarketDataEvent> implements Comparator<T> {

    @Override
    public int compare(T event, T otherEvent) {
        if (event.getTimeStampMs() < otherEvent.getTimeStampMs()) {
            return -1;
        }
        if (event.getTimeStampMs() > otherEvent.getTimeStampMs()) {
            return 1;
        }

        if (event.getTimeStampMs() == otherEvent.getTimeStampMs()) {
            if (isComparingOrders(event, otherEvent)) {
                return compare((MarketDataOrder) event, (MarketDataOrder) otherEvent);
            }
        }

        return 0;
    }

    private int compare(MarketDataOrder order, MarketDataOrder otherOrder) {
        if (order.getOrderOperationEnum().equals(OrderOperationEnum.CREATE)) {
            return -1;
        }

        if (otherOrder.getOrderOperationEnum().equals(OrderOperationEnum.CREATE)) {
            return -1;
        }

        if (order.getOrderOperationEnum().equals(OrderOperationEnum.UPDATE)) {
            return -1;
        }

        if (otherOrder.getOrderOperationEnum().equals(OrderOperationEnum.UPDATE)) {
            return -1;
        }

        if (order.getOrderOperationEnum().equals(OrderOperationEnum.DELETE)) {
            return -1;
        }

        if (otherOrder.getOrderOperationEnum().equals(OrderOperationEnum.DELETE)) {
            return -1;
        }

        return 0;
    }

    private boolean isComparingOrders(T event, T otherEvent) {
        return event.getEventTypeEnum().equals(EventTypeEnum.ORDER) &&
                otherEvent.getEventTypeEnum().equals(EventTypeEnum.ORDER);

    }
}
