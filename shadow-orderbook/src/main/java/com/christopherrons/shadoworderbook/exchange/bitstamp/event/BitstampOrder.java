package com.christopherrons.shadoworderbook.exchange.bitstamp.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeOrder;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampChannelEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampEventDescriptionEnum;
import com.christopherrons.shadoworderbook.exchange.bitstamp.client.enums.BitstampTradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.OrderOperationEnum;
import com.christopherrons.shadoworderbook.exchange.common.event.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BitstampOrder extends Event implements ExchangeOrder {

   /*  Json Structure example of live order
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

    private final String tradingPair;
    private final OrderOperationEnum orderOperationEnum;
    private long orderId;
    private int orderType;
    private long timeStampInMs;
    private double volume;
    private double price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampOrder(@JsonProperty("data") Map<String, Object> data,
                  @JsonProperty("channel") String channel,
                  @JsonProperty("event") String event) {
        super(ExchangeEnum.BITSTAMP, BitstampEventDescriptionEnum.fromValue(event), BitstampChannelEnum.extractValue(channel), EventTypeEnum.ORDER);

        this.orderOperationEnum = OrderOperationEnum.extractValue(event);
        this.tradingPair = BitstampTradingPairEnum.extractValue(channel);
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
    public String getTradingPair() {
        return tradingPair;
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
        return "BitstampOrder{" +
                "tradingPair='" + tradingPair + '\'' +
                ", orderOperationEnum=" + orderOperationEnum +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", timeStampInMs=" + timeStampInMs +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
