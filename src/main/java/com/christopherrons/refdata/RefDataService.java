package com.christopherrons.refdata;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.refdata.api.Instrument;
import com.christopherrons.refdata.cache.InstrumentCache;
import com.christopherrons.refdata.cache.MemberCache;
import com.christopherrons.refdata.cache.UserCache;
import com.christopherrons.refdata.enums.InstrumentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefDataService {

    @Autowired
    InstrumentCache instrumentCache;

    @Autowired
    MemberCache memberCache;

    @Autowired
    UserCache userCache;

    public void addRefData(final MarketDataEvent event) {
        instrumentCache.addInstrument(event.getInstrument());
        memberCache.addMember(event.getParticipant().getMember());
        userCache.addUser(event.getParticipant().getUser());
    }

    public List<Instrument> getInstruments() {
        return instrumentCache.getInstruments();
    }

    public List<String> getInstrumentsByType(final InstrumentTypeEnum instrumentTypeEnum) {
        return instrumentCache.getInstrumentByType(instrumentTypeEnum);
    }

    public List<InstrumentTypeEnum> getInstrumentTypes() {
        return instrumentCache.getInstrumentTypes();
    }
}
