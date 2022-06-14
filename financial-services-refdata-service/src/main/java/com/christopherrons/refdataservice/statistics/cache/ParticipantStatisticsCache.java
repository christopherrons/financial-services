package com.christopherrons.refdataservice.statistics.cache;

import com.christopherrons.refdataservice.statistics.model.ParticipantStatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParticipantStatisticsCache {

    private final Map<String, ParticipantStatistics> participantIdToParticipantStatistics = new ConcurrentHashMap<>();

    public ParticipantStatistics findOrCreateParticipantStatistics(final String participantId) {
        return participantIdToParticipantStatistics.computeIfAbsent(participantId, ParticipantStatistics::new);
    }

    public List<ParticipantStatistics> getParticipantStatistics() {
        return participantIdToParticipantStatistics.values().isEmpty() ?
                Collections.emptyList() :
                new ArrayList<>(participantIdToParticipantStatistics.values());
    }
}
