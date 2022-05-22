package com.christopherrons.marketdata.common.model;

import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataFeedEnum;
import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.marketdata.common.model.Order;
import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import com.christopherrons.refdata.participant.model.Member;
import com.christopherrons.refdata.participant.model.Participant;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import static com.christopherrons.refdata.participant.utils.UserGeneratorUtils.generateUser;

public class ApiOrder extends Order {
    private static final AtomicLong CURRENT_ORDER_ID = new AtomicLong(1);

    public ApiOrder(double price, double volume, int orderType,OrderOperationEnum orderOperationEnum ) {
        super(MargetDataFeedEnum.BITSTAMP,
                orderOperationEnum,
                new Participant(new Member(MargetDataFeedEnum.BITSTAMP.getName()), generateUser()),
                CURRENT_ORDER_ID.getAndIncrement(),
                orderType,
                volume,
                volume,
                price,
                Instant.now().toEpochMilli(),
                Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.XRP_USD),
                EventDescriptionEnum.ORDER_CREATED,
                ChannelEnum.LIVE_ORDERS,
                EventTypeEnum.ORDER
        );
    }

}
