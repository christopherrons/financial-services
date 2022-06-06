package com.christopherrons.refdataengine.statistics;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.enums.ParticipantAggregationLevelEum;
import com.christopherrons.common.model.refdata.Participant;
import com.christopherrons.refdataengine.statistics.cache.OrderbookStatisticsCache;
import com.christopherrons.refdataengine.statistics.cache.ParticipantStatisticsCache;
import com.christopherrons.refdataengine.statistics.model.OrderbookStatistics;
import com.christopherrons.refdataengine.statistics.model.ParticipantStatistics;
import org.springframework.stereotype.Service;

import static com.christopherrons.common.utils.ParticipantGeneratorUtils.createParticipant;

@Service
public class StatisticsService {

    private final OrderbookStatisticsCache orderbookStatisticsCache = new OrderbookStatisticsCache();
    private final ParticipantStatisticsCache participantStatisticsCache = new ParticipantStatisticsCache();


    public void updateStatistics(final MarketDataTrade trade) {
        orderbookStatisticsCache.findOrCreateOrderbookStatistics(trade.getOrderbookId()).update(trade);
        Participant bidParticipant = trade.getBidParticipant();
        Participant askParticipant = trade.getAskParticipant();
        for (ParticipantAggregationLevelEum participantAggregationLevelEum : ParticipantAggregationLevelEum.values()) {
            String bidParticipantId = createParticipant(bidParticipant, participantAggregationLevelEum).getParticipantId();
            participantStatisticsCache.findOrCreateParticipantStatistics(bidParticipantId).update(trade);
            String askParticipantId = createParticipant(askParticipant, participantAggregationLevelEum).getParticipantId();
            participantStatisticsCache.findOrCreateParticipantStatistics(askParticipantId).update(trade);
        }
    }

    public void updateStatistics(final MarketDataOrder order) {
        orderbookStatisticsCache.findOrCreateOrderbookStatistics(order.getOrderbookId()).update(order);
        Participant participant = order.getParticipant();
        for (ParticipantAggregationLevelEum participantAggregationLevelEum : ParticipantAggregationLevelEum.values()) {
            String participantId = createParticipant(participant, participantAggregationLevelEum).getParticipantId();
            participantStatisticsCache.findOrCreateParticipantStatistics(participantId).update(order);
        }
    }

    public OrderbookStatistics getOrderbookStatistics(final String orderbookId) {
        return orderbookStatisticsCache.findOrCreateOrderbookStatistics(orderbookId);
    }

    public ParticipantStatistics getParticipantStatistics(final String participantId) {
        return participantStatisticsCache.findOrCreateParticipantStatistics(participantId);
    }
}
