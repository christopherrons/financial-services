package com.christopherrons.refdata;

import com.christopherrons.marketdata.api.MarketDataEvent;
import com.christopherrons.refdata.cache.InstrumentCache;
import com.christopherrons.refdata.cache.MemberCache;
import com.christopherrons.refdata.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
