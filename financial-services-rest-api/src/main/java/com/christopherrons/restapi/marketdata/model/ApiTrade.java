package com.christopherrons.restapi.marketdata.model;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.EventDescriptionEnum;
import com.christopherrons.common.enums.marketdata.EventTypeEnum;
import com.christopherrons.common.enums.marketdata.MarketDataFeedEnum;
import com.christopherrons.common.enums.marketdata.TradingPairEnum;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.marketdata.Trade;
import com.christopherrons.common.model.refdata.Member;
import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.restapi.marketdata.requests.ApiTradeRequest;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import static com.christopherrons.common.utils.ParticipantGeneratorUtils.generateUser;

public class ApiTrade extends Trade {

    private static final AtomicLong TRADE_ORDER_ID = new AtomicLong(1);

    public ApiTrade(ApiTradeRequest apiTradeRequest) {
        this(apiTradeRequest.getMarketDataFeed(),
                apiTradeRequest.getTradingPair(),
                apiTradeRequest.getPrice(),
                apiTradeRequest.getVolume(),
                apiTradeRequest.getEventDescription(),
                apiTradeRequest.getInstrumentType(),
                apiTradeRequest.isBidSideAggressor());
    }

    public ApiTrade(MarketDataFeedEnum marketDataFeedEnum,
                    TradingPairEnum tradingPairEnum,
                    double price,
                    double volume,
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
                EventTypeEnum.TRADE
        );
    }
}
