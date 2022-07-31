package com.christopherrons.websockets.datastream;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.broadcasts.OrderEventBroadcast;
import com.christopherrons.common.broadcasts.OrderbookSnapshotBroadcast;
import com.christopherrons.common.broadcasts.TradeEventBroadcast;
import com.christopherrons.common.model.trading.OrderbookSnapshot;
import com.christopherrons.websockets.api.DataStream;
import com.christopherrons.websockets.datastream.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.christopherrons.websockets.utils.DataStreamUtils.createOrderDataStreamItem;
import static com.christopherrons.websockets.utils.DataStreamUtils.createTradeDataStreamItem;

@Service
public class MarketDataStream {
    private static final String ORDER_BOOK_ENDPOINT = "/topic/orderBook";
    private static final String ORDER_EVENT_ENDPOINT = "/topic/orderEvent";
    private static final String TRADE_ENDPOINT = "/topic/trade";
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MarketDataStream(SimpMessagingTemplate messagingTemplate) {
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
                    order.getOrderOperationEnum(),
                    order.getTimeStampMs())
            );
        }
        pushData(ORDER_EVENT_ENDPOINT, new OrderDataStream(orderDataStreamItems));
    }

    @EventListener
    public void onTradeEvent(TradeEventBroadcast tradeEventBroadcast) {
        List<TradeDataStreamItem> tradeDataStreamItems = new ArrayList<>();
        for (MarketDataTrade trade : tradeEventBroadcast.getTrades()) {
            tradeDataStreamItems.add(createTradeDataStreamItem(trade.getPrice(), trade.getVolume(), trade.isBidSideAggressor(), trade.getTimeStampMs()));
        }
        pushData(TRADE_ENDPOINT, new TradeDataStream(tradeDataStreamItems));
    }

    @EventListener
    public void onOrderbookSnapshotEvent(OrderbookSnapshotBroadcast orderbookSnapshotBroadcast) {
        List<TradeDataStreamItem> tradeDataStreamItems = new ArrayList<>();
        for (OrderbookSnapshot snapshot : orderbookSnapshotBroadcast.getOrderbookSnapshots()) {
            pushData(ORDER_BOOK_ENDPOINT, new OrderbookSnapshotStream(snapshot.getBidPriceLevelData(), snapshot.getAskPriceLevelData(), snapshot.getBestAsk(), snapshot.getBestBid()));
        }
    }

    private void pushData(final String endPoint, final DataStream dataStream) {
        if (!dataStream.isEmpty()) {
            messagingTemplate.convertAndSend(endPoint, dataStream);
        }
    }
}
