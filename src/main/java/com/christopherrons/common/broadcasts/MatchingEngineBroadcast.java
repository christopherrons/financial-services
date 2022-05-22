package com.christopherrons.common.broadcasts;

import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MatchingEngineBroadcast extends ApplicationEvent {

    private final Collection<MatchingEngineResult> matchingEngineResults;

    public MatchingEngineBroadcast(Object source, Collection<MatchingEngineResult> matchingEngineResults) {
        super(source);
        this.matchingEngineResults = matchingEngineResults;
    }

    public List<MatchingEngineResult> getMatchingEngineResult() {
        return new ArrayList<>(matchingEngineResults);
    }
}
