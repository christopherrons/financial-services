package com.christopherrons.marketdata.bitstamp.model;

import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.EventData;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.refdata.participant.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;

import java.util.Map;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

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
    private final Participant askParticipant;
    private final Participant bidParticipant;
    private final EventData eventData;
    private int tradeId;
    private long buyOrderId;
    private long sellOrderId;
    private boolean isBidSideAggressor;
    private double volume;
    private double price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampTrade(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        this.askParticipant = new Participant(
                new Member(MargetDataFeedEnum.BITSTAMP.getName()),
                generateUser()
        );
        this.bidParticipant = new Participant(
                new Member(MargetDataFeedEnum.BITSTAMP.getName()),
                generateUser()
        );

        long timeStampInMs = 0;
        if (!data.isEmpty()) {
            this.tradeId = (int) data.get("id");
            this.buyOrderId = (long) data.get("buy_order_id");
            this.sellOrderId = (long) data.get("sell_order_id");
            this.isBidSideAggressor = (int) data.get("type") == 0;
            this.volume = Double.parseDouble((String) data.get("amount_str"));
            this.price = Double.parseDouble((String) data.get("price_str"));
            timeStampInMs = Long.parseLong((String) data.get("microtimestamp")) / 1000;
        }

        this.eventData = new EventData(
                MargetDataFeedEnum.BITSTAMP,
                event,
                channel,
                EventTypeEnum.TRADE,
                timeStampInMs,
                InstrumentTypeEnum.STOCK
        );
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
    public boolean isBidSideAggressor() {
        return isBidSideAggressor;
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
    public Instrument getInstrument() {
        return eventData.getInstrument();
    }

    @Override
    public User tradeAggressorUser() {
        return isBidSideAggressor ? bidParticipant.getUser() : askParticipant.getUser();
    }

    @Override
    public Participant getAskParticipant() {
        return askParticipant;
    }

    @Override
    public Participant getBidParticipant() {
        return bidParticipant;
    }

    @Override
    public String toString() {
        return eventData.toString() + "-" + "BitstampTrade{" +
                "askParticipant=" + askParticipant +
                ", bidParticipant=" + bidParticipant +
                ", eventData=" + eventData +
                ", tradeId=" + tradeId +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", isBidSideAggressor=" + isBidSideAggressor +
                ", volume=" + volume +
                ", price=" + price +
                '}';
    }
}
