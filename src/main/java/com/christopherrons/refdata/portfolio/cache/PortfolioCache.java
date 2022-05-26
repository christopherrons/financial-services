package com.christopherrons.refdata.portfolio.cache;

import com.christopherrons.refdata.participant.model.Participant;
import com.christopherrons.refdata.portfolio.model.Portfolio;

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
