package com.christopherrons.refdata.portfolio;

import com.christopherrons.marketdata.api.MarketDataTrade;
import com.christopherrons.refdata.portfolio.cache.PortfolioCache;
import com.christopherrons.refdata.portfolio.model.Portfolio;
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
