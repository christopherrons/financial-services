package com.christopherrons.refdata.instrument.cache;

import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InstrumentCache {

    private final Map<CompositeKey, Instrument> keyToInstrument = new ConcurrentHashMap<>();
    private final Map<InstrumentTypeEnum, Set<String>> instrumentTypeToTradingPair = new ConcurrentHashMap<>();

    public void addInstrument(final Instrument instrument) {
        keyToInstrument.putIfAbsent(new CompositeKey(instrument.getInstrumentType(), instrument.getTradingPairEnum().getName()), instrument);
        instrumentTypeToTradingPair.computeIfAbsent(instrument.getInstrumentType(), k -> new HashSet<>()).add(instrument.getTradingPairEnum().getName());
    }

    public List<Instrument> getInstruments() {
        return new ArrayList<>(keyToInstrument.values());
    }

    public List<String> getInstrumentsByType(final InstrumentTypeEnum instrumentTypeEnum) {
        return !instrumentTypeToTradingPair.containsKey(instrumentTypeEnum) ? Collections.emptyList() :
                new ArrayList<>(instrumentTypeToTradingPair.get(instrumentTypeEnum));
    }

    public List<InstrumentTypeEnum> getInstrumentTypes() {
        return instrumentTypeToTradingPair.keySet().isEmpty() ? Collections.emptyList() :
                new ArrayList<>(instrumentTypeToTradingPair.keySet());
    }

    private record CompositeKey(InstrumentTypeEnum instrumentTypeEnum, String tradingPair) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompositeKey that)) return false;
            return instrumentTypeEnum == that.instrumentTypeEnum && tradingPair.equals(that.tradingPair);
        }

        @Override
        public int hashCode() {
            return Objects.hash(instrumentTypeEnum, tradingPair);
        }
    }
}
