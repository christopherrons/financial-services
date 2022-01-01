package com.christopherrons.shadoworderbook.orderbook.cache;

import com.christopherrons.shadoworderbook.orderbook.model.ShadowOrderbook;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShadowOrderbookCache {

    private final Map<CompositeKey, ShadowOrderbook> keyToOrderBook = new ConcurrentHashMap<>();

    public ShadowOrderbook findOrCreate(final String exchangeName, final String tradingPair) {
        return keyToOrderBook.computeIfAbsent(new CompositeKey(exchangeName, tradingPair), key -> new ShadowOrderbook());
    }

    private static class CompositeKey {

        private final String exchangeName;
        private final String tradingPair;

        CompositeKey(String exchangeName, String tradingPair) {
            this.exchangeName = exchangeName;
            this.tradingPair = tradingPair;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey that = (CompositeKey) o;
            return exchangeName.equals(that.exchangeName) && tradingPair.equals(that.tradingPair);
        }

        @Override
        public int hashCode() {
            return Objects.hash(exchangeName, tradingPair);
        }
    }
}
