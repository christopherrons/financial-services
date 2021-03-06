package com.christopherrons.refdataservice.participant.cache;

import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.common.model.refdata.Portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PortfolioCache {
    private final Map<String, Portfolio> participantToPortfolio = new ConcurrentHashMap<>();

    public Portfolio findOrCreatePortfolio(final Participant participant) {
        return participantToPortfolio.computeIfAbsent(participant.getParticipantId(), k -> new Portfolio(participant));
    }

    public List<Portfolio> getPortfolios() {
        return participantToPortfolio.values().isEmpty() ?
                Collections.emptyList() :
                new ArrayList<>(participantToPortfolio.values());
    }

}
