package com.christopherrons.shadoworderbook.exchange.bitstamp.event;

import com.christopherrons.shadoworderbook.exchange.api.ExchangeTrade;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.EventTypeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.event.ExchangeEnum;
import com.christopherrons.shadoworderbook.exchange.common.enums.subscription.TradingPairEnum;
import com.christopherrons.shadoworderbook.exchange.common.event.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BitstampTrade extends Event implements ExchangeTrade {

   /*  Json Structure example of Bitstamp Live Trade
    {
       "data":{
          "id":216689415,
          "timestamp":"1641719887",
          "amount":500,
          "amount_str":"500.00000000",
          "price":0.74481,
          "price_str":"0.74481",
          "type":1,
          "microtimestamp":"1641719887925000",
          "buy_order_id":1445416603234305,
          "sell_order_id":1445416604745728
       },
       "channel":"live_trades_xrpusd",
       "event":"trade"
    }*/

    private int tradeId;
    private long buyOrderId;
    private long sellOrderId;
    private int sideAggressor;
    private long timeStampInMs;
    private double volume;
    private double price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampTrade(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        super(ExchangeEnum.BITSTAMP, event, channel, EventTypeEnum.TRADE);

        if (!data.isEmpty()) {
            this.tradeId = (int) data.get("id");
            this.buyOrderId = (long) data.get("buy_order_id");
            this.sellOrderId = (long) data.get("sell_order_id");
            this.sideAggressor = (int) data.get("type");
            this.timeStampInMs = Long.parseLong((String) data.get("microtimestamp"));
            this.volume = Double.parseDouble((String) data.get("amount_str"));
            this.price = Double.parseDouble((String) data.get("price_str"));
        }
    }

    @Override
    public long getTradeId() {
        return tradeId;
    }

    @Override
    public long getBuyOrderId() {
        return buyOrderId;
    }

    @Override
    public long getSellOrderId() {
        return sellOrderId;
    }

    @Override
    public int getSideAggressor() {
        return sideAggressor;
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
        return super.toString() + "- BitstampTrade{" +
                "tradeId=" + tradeId +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", sideAggressor=" + sideAggressor +
                ", timeStampInMs=" + timeStampInMs +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
