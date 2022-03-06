package com.christopherrons.refdata.portfolio;

import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.portfolio.cache.PortfolioCache;
import org.springframework.stereotype.Service;

@Service
public class PortfolioRefDataService {

    private final PortfolioCache portfolioCache = new PortfolioCache();

    public void updatePortfolioFromTrade(final MarketDataTrade trade) {
        portfolioCache.findOrCreatePortfolio(trade.getBidParticipant()).updatePortfolio(trade);
        portfolioCache.findOrCreatePortfolio(trade.getAskParticipant()).updatePortfolio(trade);
    }
}
