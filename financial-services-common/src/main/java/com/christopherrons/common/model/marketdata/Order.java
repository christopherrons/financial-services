package com.christopherrons.common.model.marketdata;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.*;
import com.christopherrons.common.model.refdata.Participant;

import java.util.Objects;

public class Order implements MarketDataOrder {

    private final OrderOperationEnum orderOperationEnum;
    private final Participant participant;
    private final EventData eventData;
    private final long orderId;
    private final int orderType;
    private final double initialVolume;
    private final double currentVolume;
    private final double price;


    public Order(MarketDataFeedEnum marketDataFeedEnum,
                 OrderOperationEnum orderOperationEnum,
                 Participant participant,
                 long orderId,
                 int orderType,
                 double initialVolume,
                 double currentVolume,
                 double price,
                 long timeStampInMs,
                 Instrument instrument,
                 EventDescriptionEnum eventDescriptionEnum,
                 EventTypeEnum eventTypeEnum) {
        this.orderOperationEnum = orderOperationEnum;
        this.participant = participant;
        this.orderId = orderId;
        this.orderType = orderType;
        this.initialVolume = initialVolume;
        this.currentVolume = currentVolume;
        this.price = price;

        this.eventData = new EventData(
                marketDataFeedEnum,
                eventDescriptionEnum,
                eventTypeEnum,
                timeStampInMs,
                instrument
        );
    }

    @Override
    public long getTimeStampMs() {
        return eventData.getTimeStampMs();
    }

    @Override
    public TradingPairEnum getTradingPairEnum() {
        return eventData.getTradingPairEnum();
    }

    @Override
    public EventTypeEnum getEventTypeEnum() {
        return eventData.getEventTypeEnum();
    }

    @Override
    public MarketDataFeedEnum getMarketDataEnum() {
        return eventData.getMarketDataEnum();
    }

    @Override
    public EventDescriptionEnum getEventDescriptionEnum() {
        return eventData.getEventDescriptionEnum();
    }

    @Override
    public String getOrderbookId() {
        return eventData.getOrderbookId();
    }

    @Override
    public Instrument getInstrument() {
        return eventData.getInstrument();
    }

    @Override
    public OrderOperationEnum getOrderOperationEnum() {
        return orderOperationEnum;
    }

    @Override
    public int getOrderType() {
        return orderType;
    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getInitialVolume() {
        return initialVolume;
    }

    public double getCurrentVolume() {
        return currentVolume;
    }

    @Override
    public Participant getParticipant() {
        return participant;
    }

    @Override
    public boolean isFilled() {
        return currentVolume == 0;
    }

    @Override
    public double getOrderValue() {
        return price * initialVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return eventData + "-" + "Order{" +
                "orderOperationEnum=" + orderOperationEnum +
                ", participant=" + participant +
                ", eventData=" + eventData +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", volume=" + initialVolume +
                ", price=" + price +
                '}';
    }
}
