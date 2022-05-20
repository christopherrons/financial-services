package com.christopherrons.common.broadcasts;

import com.christopherrons.tradingengine.matchingengine.model.MatchingEngineResult;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class OrderbookUpdateBroadcast extends ApplicationEvent {

    private final List<MatchingEngineResult> matchingEngineResults;

    public OrderbookUpdateBroadcast(Object source, List<MatchingEngineResult> matchingEngineResults) {
        super(source);
        this.matchingEngineResults = matchingEngineResults;
    }

    public List<MatchingEngineResult> getMatchingEngineResults() {
        return matchingEngineResults;
    }
}
