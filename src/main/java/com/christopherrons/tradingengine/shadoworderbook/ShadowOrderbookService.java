package com.christopherrons.tradingengine.shadoworderbook;


import com.christopherrons.marketdata.api.MarketDataOrder;
import com.christopherrons.tradingengine.shadoworderbook.cache.ShadowOrderbookCache;
import com.christopherrons.tradingengine.shadoworderbook.model.ShadowOrderbook;
import org.springframework.stereotype.Service;

@Service
public class ShadowOrderbookService {

    private final ShadowOrderbookCache shadowOrderbookCache = new ShadowOrderbookCache();

    public ShadowOrderbook updateAndGetOrderbook(MarketDataOrder order) {
        final ShadowOrderbook shadowOrderbook = shadowOrderbookCache.findOrCreate(order.getOrderbookId());
        switch (order.getOrderOperationEnum()) {
            case UPDATE:
                break;
            case CREATE:
                break;
            case DELETE:
                break;
            default:
                break;
        }
        return shadowOrderbook;
    }
}
