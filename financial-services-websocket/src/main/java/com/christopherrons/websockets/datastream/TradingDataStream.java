package com.christopherrons.websockets.datastream;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.enums.marketdata.OrderOperationEnum;
import com.christopherrons.common.enums.marketdata.OrderTypeEnum;
import com.christopherrons.websockets.api.DataStream;
import com.christopherrons.websockets.datastream.model.OrderDataStream;
import com.christopherrons.websockets.datastream.model.OrderDataStreamItem;
import com.christopherrons.websockets.datastream.model.TradeDataStream;
import com.christopherrons.websockets.datastream.model.TradeDataStreamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.christopherrons.websockets.utils.DataStreamUtils.createOrderDataStreamItem;
import static com.christopherrons.websockets.utils.DataStreamUtils.createTradeDataStreamItem;

@Service
public class TradingDataStream {
    private static final String ORDER_BOOK_ENDPOINT = "/topic/orderBook";
    private static final String TRADE_ENDPOINT = "/topic/trade";
    private final SimpMessagingTemplate messagingTemplate;

    //TODO: Store in DataStream in timeBoundlist to set update interval


    @Autowired
    public TradingDataStream(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void onOrderEvent(OrderEventBroadcast orderEventBroadcast) {
        List<MarketDataOrder> orders = orderEventBroadcast.getOrders();
        List<OrderDataStreamItem> orderDataStreamItems = new ArrayList<>();
        for (MarketDataOrder order : orders) {
            orderDataStreamItems.add(createOrderDataStreamItem(
                    order.getPrice(),
                    order.getCurrentVolume(),
                    order.getOrderType(),
                    order.getOrderOperationEnum())
            );
        }
        pushData(ORDER_BOOK_ENDPOINT, new OrderDataStream(orderDataStreamItems));
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast tradeEventBroadcast) {
        List<OrderDataStreamItem> orderDataStreamItems = new ArrayList<>();
        List<TradeDataStreamItem> tradeDataStreamItems = new ArrayList<>();
        addDataStreamItems(tradeDataStreamItems, orderDataStreamItems, tradeEventBroadcast.getTrades());
        pushData(ORDER_BOOK_ENDPOINT, new OrderDataStream(orderDataStreamItems));
        pushData(TRADE_ENDPOINT, new TradeDataStream(tradeDataStreamItems));
    }

    private void addDataStreamItems(final List<TradeDataStreamItem> tradeDataStreamItems,
                                    final List<OrderDataStreamItem> orderDataStreamItems,
                                    final List<MarketDataTrade> trades) {
        for (MarketDataTrade trade : trades) {
            tradeDataStreamItems.add(createTradeDataStreamItem(trade.getPrice(), trade.getVolume(), trade.isBidSideAggressor()));
            orderDataStreamItems.add(createOrderDataStreamItem(trade.getPrice(), trade.getVolume(),
                    OrderTypeEnum.INVALID_ORDER_TYPE.getValue(), OrderOperationEnum.DELETE));
        }
    }

    private void pushData(final String endPoint, final DataStream dataStream) {
        if (dataStream.isEmpty()) {
            messagingTemplate.convertAndSend(endPoint, dataStream);
        }
    }
}
