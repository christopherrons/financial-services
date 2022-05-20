package com.christopherrons.tradingengine.orderbook;


import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.orderbook.cache.OrderbookCache;
import com.christopherrons.tradingengine.orderbook.api.Orderbook;
import org.springframework.stereotype.Service;

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
}
