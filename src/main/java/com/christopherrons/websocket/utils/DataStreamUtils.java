package com.christopherrons.websocket.utils;

import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;
import com.christopherrons.websocket.model.OrderDataStreamItem;
import com.christopherrons.websocket.model.TradeDataStreamItem;

public class DataStreamUtils {

    private DataStreamUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static OrderDataStreamItem createOrderDataStreamItem(final double price,
                                                                final double volume,
                                                                final int orderType) {
        return new OrderDataStreamItem(
                price,
                volume,
                OrderTypeEnum.fromValue(orderType)
        );
    }

    public static TradeDataStreamItem createTradeDataStreamItem(final double price,
                                                                final double volume,
                                                                final boolean isBidSideAggressor) {
        return new TradeDataStreamItem(
                price,
                volume,
                isBidSideAggressor);
    }
}
