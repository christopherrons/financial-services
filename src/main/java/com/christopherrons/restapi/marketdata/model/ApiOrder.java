package com.christopherrons.restapi.marketdata.model;

import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MarketDataFeedEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.Order;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.restapi.marketdata.requests.ApiOrderRequest;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

public class ApiOrder extends Order {
    private static final AtomicLong CURRENT_ORDER_ID = new AtomicLong(1);

    public ApiOrder(ApiOrderRequest apiOrderRequest) {
        this(apiOrderRequest.getMarketDataFeed(),
                apiOrderRequest.getTradingPair(),
                apiOrderRequest.getPrice(),
                apiOrderRequest.getVolume(),
                apiOrderRequest.getOrderType().getValue(),
                apiOrderRequest.getOrderOperation(),
                apiOrderRequest.getChannel(),
                apiOrderRequest.getEventDescription(),
                apiOrderRequest.getInstrumentType()
        );
    }

    public ApiOrder(MarketDataFeedEnum marketDataFeedEnum,
                    TradingPairEnum tradingPairEnum,
                    double price,
                    double volume,
                    int orderType,
                    OrderOperationEnum orderOperationEnum,
                    ChannelEnum channelEnum,
                    EventDescriptionEnum eventDescriptionEnum,
                    InstrumentTypeEnum instrumentTypeEnum) {
        super(marketDataFeedEnum,
                orderOperationEnum,
                new Participant(new Member(marketDataFeedEnum.getName()), generateUser()),
                CURRENT_ORDER_ID.getAndIncrement(),
                orderType,
                volume,
                volume,
                price,
                Instant.now().toEpochMilli(),
                Instrument.createInstrument(instrumentTypeEnum, tradingPairEnum),
                eventDescriptionEnum,
                channelEnum,
                EventTypeEnum.ORDER
        );
    }

}
