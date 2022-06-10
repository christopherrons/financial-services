package com.christopherrons.restapi.marketdata.model;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.*;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.marketdata.Order;
import com.christopherrons.common.model.refdata.Member;
import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.restapi.marketdata.requests.ApiOrderRequest;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import static com.christopherrons.common.utils.ParticipantGeneratorUtils.getUserFromPool;

public class ApiOrder extends Order {
    private static final AtomicLong CURRENT_ORDER_ID = new AtomicLong(1);

    public ApiOrder(ApiOrderRequest apiOrderRequest) {
        this(apiOrderRequest.getMarketDataFeed(),
                apiOrderRequest.getTradingPair(),
                apiOrderRequest.getPrice(),
                apiOrderRequest.getVolume(),
                apiOrderRequest.getOrderType().getValue(),
                apiOrderRequest.getOrderOperation(),
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
                    EventDescriptionEnum eventDescriptionEnum,
                    InstrumentTypeEnum instrumentTypeEnum) {
        super(marketDataFeedEnum,
                orderOperationEnum,
                new Participant(new Member(marketDataFeedEnum.getName()), getUserFromPool()),
                CURRENT_ORDER_ID.getAndIncrement(),
                orderType,
                volume,
                volume,
                price,
                Instant.now().toEpochMilli(),
                Instrument.createInstrument(instrumentTypeEnum, tradingPairEnum),
                eventDescriptionEnum,
                EventTypeEnum.ORDER
        );
    }

}
