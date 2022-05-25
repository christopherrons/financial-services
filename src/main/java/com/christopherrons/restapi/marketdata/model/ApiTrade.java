package com.christopherrons.restapi.marketdata.model;

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
import com.christopherrons.restapi.marketdata.requests.ApiTradeRequest;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

public class ApiTrade extends Trade {

    private static final AtomicLong TRADE_ORDER_ID = new AtomicLong(1);

    public ApiTrade(ApiTradeRequest apiTradeRequest) {
        this(apiTradeRequest.getMarketDataFeed(),
                apiTradeRequest.getTradingPair(),
                apiTradeRequest.getPrice(),
                apiTradeRequest.getVolume(),
                apiTradeRequest.getChannel(),
                apiTradeRequest.getEventDescription(),
                apiTradeRequest.getInstrumentType(),
                apiTradeRequest.isBidSideAggressor());
    }

    public ApiTrade(MarketDataFeedEnum marketDataFeedEnum,
                    TradingPairEnum tradingPairEnum,
                    double price,
                    double volume,
                    ChannelEnum channelEnum,
                    EventDescriptionEnum eventDescriptionEnum,
                    InstrumentTypeEnum instrumentTypeEnum,
                    boolean isBidSideAggressor) {
        super(marketDataFeedEnum,
                new Participant(new Member(marketDataFeedEnum.getName()), generateUser()),
                new Participant(new Member(marketDataFeedEnum.getName()), generateUser()),
                TRADE_ORDER_ID.getAndIncrement(),
                TRADE_ORDER_ID.get() + 1,
                TRADE_ORDER_ID.get() + 2,
                isBidSideAggressor,
                volume,
                price,
                Instant.now().toEpochMilli(),
                Instrument.createInstrument(instrumentTypeEnum, tradingPairEnum),
                eventDescriptionEnum,
                channelEnum,
                EventTypeEnum.TRADE
        );
    }
}
