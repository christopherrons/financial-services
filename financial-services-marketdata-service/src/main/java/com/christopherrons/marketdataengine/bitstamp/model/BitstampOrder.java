package com.christopherrons.marketdataengine.bitstamp.model;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.*;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.marketdata.Order;
import com.christopherrons.common.model.refdata.Member;
import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.marketdataengine.common.enums.ChannelEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import static com.christopherrons.common.utils.ParticipantGeneratorUtils.generateUser;

public class BitstampOrder extends Order {

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


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    BitstampOrder(@JsonProperty("data") Map<String, Object> data, @JsonProperty("channel") String channel, @JsonProperty("event") String event) {
        super(MarketDataFeedEnum.BITSTAMP, OrderOperationEnum.extractValue(event),
                new Participant(new Member(MarketDataFeedEnum.BITSTAMP.getName()), generateUser()),
                !data.isEmpty() ? Long.parseLong((String) data.get("id_str")) : -1L,
                !data.isEmpty() ? (int) data.get("order_type") : -1,
                !data.isEmpty() ? Double.parseDouble((String) data.get("amount_str")) : -1.0,
                !data.isEmpty() ? Double.parseDouble((String) data.get("amount_str")) : -1.0,
                !data.isEmpty() ? Double.parseDouble((String) data.get("price_str")) : -1.0,
                !data.isEmpty() ? Long.parseLong((String) data.get("microtimestamp")) / 1000 : -1L,
                Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.inferTradingPairEnum(channel, MarketDataFeedEnum.BITSTAMP)),
                EventDescriptionEnum.inferEventDescriptionEnum(event, MarketDataFeedEnum.BITSTAMP),
                EventTypeEnum.ORDER
        );
    }

}
