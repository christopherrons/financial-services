package com.christopherrons.refdataservice.participant;

import com.christopherrons.common.api.marketdata.MarketDataOrder;
import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.model.refdata.Portfolio;
import com.christopherrons.refdataservice.participant.cache.MemberCache;
import com.christopherrons.refdataservice.participant.cache.ParticipantOrderDataCache;
import com.christopherrons.refdataservice.participant.cache.PortfolioCache;
import com.christopherrons.refdataservice.participant.cache.UserCache;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantRefDataService {

    private final MemberCache memberCache = new MemberCache();
    private final UserCache userCache = new UserCache();
    private final PortfolioCache portfolioCache = new PortfolioCache();
    private final ParticipantOrderDataCache participantOrderDataCache = new ParticipantOrderDataCache();

    public void handleOrder(final MarketDataOrder order) {
        var participant = order.getParticipant();
        memberCache.addMember(participant.member());
        userCache.addUser(participant.user());
        participantOrderDataCache.findOrCreateParticipantData(participant).addOrderId(order.getOrderbookId());
    }

    public void handleTrade(final MarketDataTrade trade) {
        updatePortfolios(trade);
    }

    private void updatePortfolios(final MarketDataTrade trade) {
        portfolioCache.findOrCreatePortfolio(trade.getBidParticipant())
                .updatePortfolio(trade.getInstrument().getInstrumentId(), trade.getVolume(), true);
        portfolioCache.findOrCreatePortfolio(trade.getAskParticipant())
                .updatePortfolio(trade.getInstrument().getInstrumentId(), trade.getVolume(), false);
    }

    public List<Portfolio> getPortfolios() {
        return portfolioCache.getPortfolios();
    }
}
