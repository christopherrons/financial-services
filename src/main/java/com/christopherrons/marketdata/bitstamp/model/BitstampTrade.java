package com.christopherrons.marketdata.bitstamp.model;

import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.EventData;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.model.participant.Member;
import com.christopherrons.refdata.model.participant.Participant;
import com.christopherrons.refdata.model.participant.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;

import java.util.Map;

public class BitstampTrade implements MarketDataTrade {

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

    private static final Faker nameFaker = new Faker();
    private EventData eventData;
    private int tradeId;
    private long buyOrderId;
    private long sellOrderId;
    private int sideAggressor;
    private double volume;
    private double price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampTrade(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        if (!data.isEmpty()) {
            this.tradeId = (int) data.get("id");
            this.buyOrderId = (long) data.get("buy_order_id");
            this.sellOrderId = (long) data.get("sell_order_id");
            this.sideAggressor = (int) data.get("type");
            this.volume = Double.parseDouble((String) data.get("amount_str"));
            this.price = Double.parseDouble((String) data.get("price_str"));
            this.eventData = new EventData(
                    MargetDataFeedEnum.BITSTAMP,
                    event,
                    channel,
                    EventTypeEnum.TRADE,
                    Long.parseLong((String) data.get("microtimestamp")) / 1000,
                    new Participant(
                            new Member(MargetDataFeedEnum.BITSTAMP.getName()),
                            new User(nameFaker.name().firstName(), nameFaker.name().lastName())
                    ),
                    InstrumentTypeEnum.STOCK
            );
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
        return eventData.getParticipant();
    }

    @Override
    public Instrument getInstrument() {
        return eventData.getInstrument();
    }

    @Override
    public String toString() {
        return eventData.toString() + "-" + "BitstampTrade{" +
                "event=" + eventData +
                ", tradeId=" + tradeId +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", sideAggressor=" + sideAggressor +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
