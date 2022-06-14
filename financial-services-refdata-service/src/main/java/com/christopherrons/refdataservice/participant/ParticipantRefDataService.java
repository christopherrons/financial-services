package com.christopherrons.refdataservice.participant;

import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.refdataservice.participant.cache.MemberCache;
import com.christopherrons.refdataservice.participant.cache.UserCache;
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
