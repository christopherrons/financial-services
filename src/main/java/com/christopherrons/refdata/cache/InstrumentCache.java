package com.christopherrons.refdata.cache;

import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InstrumentCache {

    private final Map<CompositeKey, Instrument> keyToInstrument = new ConcurrentHashMap<>();

    public void addInstrument(final Instrument instrument) {
        keyToInstrument.putIfAbsent(new CompositeKey(instrument.getInstrumentType(), instrument.getTradingPair()), instrument);
    }

    private static class CompositeKey {

        private final InstrumentType instrumentType;
        private final String tradingPair;

        public CompositeKey(InstrumentType instrumentType, String tradingPair) {
            this.instrumentType = instrumentType;
            this.tradingPair = tradingPair;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey that = (CompositeKey) o;
            return instrumentType == that.instrumentType && tradingPair.equals(that.tradingPair);
        }

        @Override
        public int hashCode() {
            return Objects.hash(instrumentType, tradingPair);
        }
    }
}
