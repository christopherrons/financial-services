package com.christopherrons.marketdata.bitstamp.model;

import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.Trade;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

public class BitstampTrade extends Trade {

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



    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampTrade(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        super(MarketDataFeedEnum.BITSTAMP,
                new Participant(new Member(MarketDataFeedEnum.BITSTAMP.getName()), generateUser()),
                new Participant(new Member(MarketDataFeedEnum.BITSTAMP.getName()), generateUser()),
                !data.isEmpty() ? (int) data.get("id") : -1,
                !data.isEmpty() ? (long) data.get("buy_order_id") : -1L,
                !data.isEmpty() ? (long) data.get("sell_order_id") : -1L,
                !data.isEmpty() ? (int) data.get("type") == 0 : false,
                !data.isEmpty() ? Double.parseDouble((String) data.get("amount_str")) : -1.0,
                !data.isEmpty() ? Double.parseDouble((String) data.get("price_str")) : -1.0,
                !data.isEmpty() ? Long.parseLong((String) data.get("microtimestamp")) / 1000 : -1L,
                Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.inferTradingPairEnum(channel, MarketDataFeedEnum.BITSTAMP)),
                EventDescriptionEnum.inferEventDescriptionEnum(event, MarketDataFeedEnum.BITSTAMP),
                ChannelEnum.inferChannelEnum(channel, MarketDataFeedEnum.BITSTAMP),
                EventTypeEnum.ORDER
        );
    }
}
