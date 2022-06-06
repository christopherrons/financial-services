package com.christopherrons.tradingengine.orderbook;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.marketdata.*;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.common.model.marketdata.Order;
import com.christopherrons.common.model.marketdata.Trade;
import com.christopherrons.common.model.refdata.Member;
import com.christopherrons.common.model.refdata.Participant;

import static com.christopherrons.common.utils.ParticipantGeneratorUtils.generateUser;

public class EventCreatorUtils {

    public static MarketDataOrder buildMarketDataOrderCreate(MarketDataFeedEnum marketDataFeedEnum,
                                                             long timeStampInMs,
                                                             double price,
                                                             double volume,
                                                             OrderTypeEnum orderTypeEnum,
                                                             long orderId) {
        return buildMarketDataOrder(marketDataFeedEnum, OrderOperationEnum.CREATE, EventDescriptionEnum.ORDER_CREATED,
                timeStampInMs, price, volume, orderTypeEnum, orderId);
    }

    public static MarketDataOrder buildMarketDataOrderRemove(MarketDataFeedEnum marketDataFeedEnum,
                                                             long timeStampInMs,
                                                             double price,
                                                             double volume,
                                                             OrderTypeEnum orderTypeEnum,
                                                             long orderId) {
        return buildMarketDataOrder(marketDataFeedEnum, OrderOperationEnum.DELETE, EventDescriptionEnum.ORDER_DELETED,
                timeStampInMs, price, volume, orderTypeEnum, orderId);
    }

    public static MarketDataOrder buildMarketDataOrder(MarketDataFeedEnum marketDataFeedEnum,
                                                       OrderOperationEnum orderOperationEnum,
                                                       EventDescriptionEnum eventDescriptionEnum,
                                                       long timeStampInMs,
                                                       double price,
                                                       double volume,
                                                       OrderTypeEnum orderTypeEnum,
                                                       long orderId) {
        return new Order(marketDataFeedEnum,
                orderOperationEnum,
                new Participant(new Member(marketDataFeedEnum.getName()), generateUser()),
                orderId,
                orderTypeEnum.getValue(),
                volume,
                volume,
                price,
                timeStampInMs,
                Instrument.createInstrument(InstrumentTypeEnum.STOCK, TradingPairEnum.BTC_USD),
                eventDescriptionEnum,
                EventTypeEnum.ORDER);
    }

    public static MarketDataTrade buildMarketDataTrade(MarketDataFeedEnum marketDataFeedEnum,
                                                       long timeStampInMs,
                                                       double price,
                                                       double volume,
                                                       boolean isBidSideAggressor,
                                                       long tradeId,
                                                       Instrument instrument,
                                                       Participant bidParticipant,
                                                       Participant askParticipant) {
        return new Trade(marketDataFeedEnum,
                bidParticipant,
                askParticipant,
                tradeId,
                tradeId,
                tradeId,
                isBidSideAggressor,
                volume,
                price,
                timeStampInMs,
                instrument,
                EventDescriptionEnum.TRADE,
                EventTypeEnum.TRADE);
    }
}
