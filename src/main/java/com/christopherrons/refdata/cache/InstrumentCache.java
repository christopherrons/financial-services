package com.christopherrons.refdata.cache;

import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InstrumentCache {

    private final Map<CompositeKey, Instrument> keyToInstrument = new ConcurrentHashMap<>();
    private final Map<InstrumentTypeEnum, Set<String>> instrumentTypeToTradingPair = new ConcurrentHashMap<>();

    public void addInstrument(final Instrument instrument) {
        keyToInstrument.putIfAbsent(new CompositeKey(instrument.getInstrumentType(), instrument.getTradingPair()), instrument);
        instrumentTypeToTradingPair.computeIfAbsent(instrument.getInstrumentType(), k -> new HashSet<>()).add(instrument.getTradingPair());
    }

    public List<Instrument> getInstruments() {
        return new ArrayList<>(keyToInstrument.values());
    }

    public List<String> getInstrumentByType(final InstrumentTypeEnum instrumentTypeEnum) {
        return !instrumentTypeToTradingPair.containsKey(instrumentTypeEnum) ? Collections.emptyList() :
                new ArrayList<>(instrumentTypeToTradingPair.get(instrumentTypeEnum));
    }

    public List<InstrumentTypeEnum> getInstrumentTypes() {
        return instrumentTypeToTradingPair.keySet().isEmpty() ? Collections.emptyList() :
                new ArrayList<>(instrumentTypeToTradingPair.keySet());
    }

    private static class CompositeKey {

        private final InstrumentTypeEnum instrumentTypeEnum;
        private final String tradingPair;

        public CompositeKey(InstrumentTypeEnum instrumentTypeEnum, String tradingPair) {
            this.instrumentTypeEnum = instrumentTypeEnum;
            this.tradingPair = tradingPair;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey)) return false;
            CompositeKey that = (CompositeKey) o;
            return instrumentTypeEnum == that.instrumentTypeEnum && tradingPair.equals(that.tradingPair);
        }

        @Override
        public int hashCode() {
            return Objects.hash(instrumentTypeEnum, tradingPair);
        }
    }
}
