package com.christopherrons.marketdata.bitstamp.model;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.EventData;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

public class BitstampOrder implements MarketDataOrder {

   /*  Json Structure example of Bitstamp Live Order
   {
        "data": {
                 "id": 1441935087595520,
                "id_str": "1441935087595520",
                "order_type": 1,
                "datetime": "1640869916",
                "microtimestamp": "1640869915619000",
                "amount": 2162.61269107,
                "amount_str": "2162.61269107",
                "price": 0.84604,
                "price_str": "0.84604"
    },
        "channel": "live_orders_xrpusd",
        "event": "order_deleted"
    }*/


    private final OrderOperationEnum orderOperationEnum;
    private final Participant participant;
    private final EventData eventData;
    private long orderId;
    private int orderType;
    private double volume;
    private double price;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampOrder(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        this.orderOperationEnum = OrderOperationEnum.extractValue(event);
        this.participant = new Participant(
                new Member(MargetDataFeedEnum.BITSTAMP.getName()),
                generateUser()
        );

        long timeStampInMs = 0;
        if (!data.isEmpty()) {
            this.orderId = Long.parseLong((String) data.get("id_str"));
            this.orderType = (int) data.get("order_type");
            this.volume = Double.parseDouble((String) data.get("amount_str"));
            this.price = Double.parseDouble((String) data.get("price_str"));
            timeStampInMs = Long.parseLong((String) data.get("microtimestamp")) / 1000;
        }
        this.eventData = new EventData(
                MargetDataFeedEnum.BITSTAMP,
                event,
                channel,
                EventTypeEnum.ORDER,
                timeStampInMs,
                InstrumentTypeEnum.STOCK
        );
    }

    @Override
    public OrderOperationEnum getOrderOperationEnum() {
        return orderOperationEnum;
    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public int getOrderType() {
        return orderType;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public double getPrice() {
        return price;
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
    public ChannelEnum getChannelEnum() {
        return eventData.getChannelEnum();
    }

    @Override
    public MargetDataFeedEnum getMarketDataEnum() {
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
    public Participant getParticipant() {
        return participant;
    }

    @Override
    public Instrument getInstrument() {
        return eventData.getInstrument();
    }

    @Override
    public String toString() {
        return eventData.toString() + "-" + "BitstampOrder{" +
                "orderOperationEnum=" + orderOperationEnum +
                ", participant=" + participant +
                ", eventData=" + eventData +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
