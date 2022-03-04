package com.christopherrons.marketdata.api;

import com.christopherrons.common.api.HasTimeStamp;
import com.christopherrons.marketdata.common.enums.event.EventDescriptionEnum;
import com.christopherrons.marketdata.common.enums.event.EventTypeEnum;
import com.christopherrons.marketdata.common.enums.event.MargetDataEnum;
import com.christopherrons.marketdata.common.enums.subscription.ChannelEnum;
import com.christopherrons.marketdata.common.enums.subscription.TradingPairEnum;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.model.participant.Participant;

public interface MarketDataEvent extends HasTimeStamp {

    TradingPairEnum getTradingPairEnum();

    EventTypeEnum getEventTypeEnum();

    ChannelEnum getChannelEnum();

    MargetDataEnum getMarketDataEnum();

    EventDescriptionEnum getEventDescriptionEnum();

    String getOrderbookId();

    Participant getParticipant();

    Instrument getInstrument();

}
