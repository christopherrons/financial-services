package com.christopherrons.refdata.participant;

import com.christopherrons.refdata.participant.cache.MemberCache;
import com.christopherrons.refdata.participant.cache.UserCache;
import com.christopherrons.refdata.participant.model.Participant;
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
