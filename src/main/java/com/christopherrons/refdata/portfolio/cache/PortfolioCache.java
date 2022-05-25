package com.christopherrons.refdata.portfolio.cache;

import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.refdata.portfolio.model.ParticipantPortfolio;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PortfolioCache {

    private final Map<String, ParticipantPortfolio> participantToPortfolio = new ConcurrentHashMap<>();

    public ParticipantPortfolio findOrCreatePortfolio(final Participant participant) {
        return participantToPortfolio.computeIfAbsent(participant.getParticipantId(), k -> new ParticipantPortfolio(participant));
    }
}
