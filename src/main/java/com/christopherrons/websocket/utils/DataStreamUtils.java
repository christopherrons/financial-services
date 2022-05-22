package com.christopherrons.websocket.utils;

import com.christopherrons.marketdata.common.enums.event.OrderOperationEnum;
import com.christopherrons.marketdata.common.enums.event.OrderTypeEnum;
import com.christopherrons.websocket.datastream.model.OrderDataStreamItem;
import com.christopherrons.websocket.datastream.model.TradeDataStreamItem;

public class DataStreamUtils {

    private DataStreamUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static OrderDataStreamItem createOrderDataStreamItem(final double price,
                                                                final double volume,
                                                                final int orderType,
                                                                final OrderOperationEnum orderOperationEnum) {
        return new OrderDataStreamItem(
                price,
                volume,
                OrderTypeEnum.fromValue(orderType),
                orderOperationEnum
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