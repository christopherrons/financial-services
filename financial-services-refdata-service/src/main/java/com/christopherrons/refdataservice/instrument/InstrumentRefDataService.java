package com.christopherrons.refdataservice.instrument;

import com.christopherrons.common.api.refdata.Instrument;
import com.christopherrons.common.enums.refdata.InstrumentTypeEnum;
import com.christopherrons.refdataservice.instrument.cache.InstrumentCache;
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
