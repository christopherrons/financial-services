package com.christopherrons.tradingengine.orderbook;


import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
import com.christopherrons.tradingengine.orderbook.cache.OrderbookCache;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderbookService {

    private final OrderbookCache orderbookCache = new OrderbookCache();

    public Orderbook updateAndGetOrderbook(MarketDataOrder order) {
        final Orderbook orderbook = orderbookCache.findOrCreate(order.getOrderbookId());
        switch (order.getOrderOperationEnum()) {
            case CREATE:
                orderbook.addOrder(order);
                break;
            case UPDATE:
                orderbook.updateOrder(order);
                break;
            case DELETE:
                orderbook.removeOrder(order);
                break;
            default:
                break;
        }
        return orderbook;
    }

    public Collection<Orderbook> getAllOrderbooks() {
        return orderbookCache.getAllOrderbooks();
    }
}
