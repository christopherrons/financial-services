package com.christopherrons.refdataengine.participant;

import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.refdataengine.participant.cache.MemberCache;
import com.christopherrons.refdataengine.participant.cache.UserCache;
import org.springframework.stereotype.Service;

@Service
public class ParticipantRefDataService {

    private final MemberCache memberCache = new MemberCache();
    private final UserCache userCache = new UserCache();

    public void addParticipant(final Participant participant) {
        memberCache.addMember(participant.getMember());
        userCache.addUser(participant.getUser());
    }
}
