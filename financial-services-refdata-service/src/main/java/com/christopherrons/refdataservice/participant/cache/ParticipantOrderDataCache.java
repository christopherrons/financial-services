package com.christopherrons.refdataservice.participant.cache;

import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.common.model.refdata.ParticipantData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ParticipantOrderDataCache {

    private final Map<String, ParticipantData> participantIdToParticipantData = new ConcurrentHashMap<>();

    public ParticipantData findOrCreateParticipantData(final Participant participant) {
        return participantIdToParticipantData.computeIfAbsent(participant.getParticipantId(), k -> new ParticipantData(participant));
    }
}
