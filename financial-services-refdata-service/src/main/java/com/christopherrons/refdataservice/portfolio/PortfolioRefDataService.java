package com.christopherrons.refdataservice.portfolio;

import com.christopherrons.common.api.marketdata.MarketDataTrade;
import com.christopherrons.common.model.refdata.Portfolio;
import com.christopherrons.refdataservice.portfolio.cache.PortfolioCache;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioRefDataService {

    private final PortfolioCache portfolioCache = new PortfolioCache();

    public void updatePortfolioFromTrade(final MarketDataTrade trade) {
        portfolioCache.findOrCreatePortfolio(trade.getBidParticipant())
                .updatePortfolio(trade.getInstrument().getInstrumentId(), trade.getVolume(), true);
        portfolioCache.findOrCreatePortfolio(trade.getAskParticipant())
                .updatePortfolio(trade.getInstrument().getInstrumentId(), trade.getVolume(), false);
    }

    public List<Portfolio> getPortfolios() {
        return portfolioCache.getPortfolios();
    }
}
