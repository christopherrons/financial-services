package com.christopherrons.refdata.instrument;

import com.christopherrons.refdata.instrument.api.Instrument;
import com.christopherrons.refdata.instrument.cache.InstrumentCache;
import com.christopherrons.refdata.instrument.enums.InstrumentTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstrumentRefDataService {

    private final InstrumentCache instrumentCache = new InstrumentCache();

    public void addInstrument(final Instrument instrument) {
        instrumentCache.addInstrument(instrument);
    }

    public List<Instrument> getInstruments() {
        return instrumentCache.getInstruments();
    }

    public List<String> getInstrumentsByType(final InstrumentTypeEnum instrumentTypeEnum) {
        return instrumentCache.getInstrumentsByType(instrumentTypeEnum);
    }

    public List<InstrumentTypeEnum> getInstrumentTypes() {
        return instrumentCache.getInstrumentTypes();
    }

}
