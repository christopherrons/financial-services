package com.christopherrons.tradingengine.matchingengine.utils;

import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.marketdata.common.model.Order;
import com.christopherrons.marketdata.common.model.Trade;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class MatchingEngineUtils {
    private static final AtomicLong CURRENT_TRADE_ID = new AtomicLong(1);

    public static MarketDataTrade buildTrade(final MarketDataOrder bidOrder,
                                             final MarketDataOrder askOrder,
                                             final double tradeVolume) {
        boolean isBidSideAggressor = bidOrder.getTimeStampMs() >= askOrder.getTimeStampMs();
        return new Trade(bidOrder.getMarketDataEnum(),
                bidOrder.getParticipant(),
                askOrder.getParticipant(),
                CURRENT_TRADE_ID.getAndIncrement(),
                bidOrder.getOrderId(),
                askOrder.getOrderId(),
                isBidSideAggressor,
                tradeVolume,
                isBidSideAggressor ? askOrder.getPrice() : bidOrder.getPrice(),
                Instant.now().toEpochMilli(),
                bidOrder.getInstrument(),
                bidOrder.getEventDescriptionEnum(),
                bidOrder.getChannelEnum(),
                bidOrder.getEventTypeEnum()
        );
    }

    public static MarketDataOrder buildUpdateOrder(final MarketDataOrder order, final double tradeVolume) {
        return new Order(order.getMarketDataEnum(),
                order.getOrderOperationEnum(),
                order.getParticipant(),
                order.getOrderId(),
                order.getOrderType(),
                order.getInitialVolume(),
                order.getCurrentVolume() - tradeVolume,
                order.getPrice(),
                order.getTimeStampMs(),
                order.getInstrument(),
                order.getEventDescriptionEnum(),
                order.getChannelEnum(),
                order.getEventTypeEnum());
    }
}

