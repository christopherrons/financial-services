package com.christopherrons.shadoworderbook.exchange.bitstamp.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeOrder;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.OrderOperationEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.event.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BitstampOrder extends Event implements ExchangeOrder {

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
    private long orderId;
    private int orderType;
    private long timeStampInMs;
    private double volume;
    private double price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampOrder(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        super(ExchangeEnum.BITSTAMP, event, channel, EventTypeEnum.ORDER);

        this.orderOperationEnum = OrderOperationEnum.extractValue(event);

        if (!data.isEmpty()) {
            this.orderId = Long.parseLong((String) data.get("id_str"));
            this.orderType = (int) data.get("order_type");
            this.timeStampInMs = Long.parseLong((String) data.get("microtimestamp"));
            this.volume = Double.parseDouble((String) data.get("amount_str"));
            this.price = Double.parseDouble((String) data.get("price_str"));
        }
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
    public long getTimeStampInMs() {
        return timeStampInMs;
    }

    @Override
    public TradingPairEnum getTradingPair() {
        return super.getTradingPairEnum();
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
    public String toString() {
        return super.toString() + "- BitstampOrder{" +
                "orderOperationEnum=" + orderOperationEnum +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", timeStampInMs=" + timeStampInMs +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
